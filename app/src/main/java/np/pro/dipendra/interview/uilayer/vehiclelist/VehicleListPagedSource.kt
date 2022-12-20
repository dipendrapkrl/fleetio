package np.pro.dipendra.interview.uilayer.vehiclelist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import np.pro.dipendra.interview.datalayer.repository.Answer.Error
import np.pro.dipendra.interview.datalayer.repository.Answer.Success
import np.pro.dipendra.interview.datalayer.repository.vehicles.VehiclesRepository
import javax.inject.Inject

class VehicleListPagedSource @Inject constructor(private val vehiclesRepository: VehiclesRepository) :
    PagingSource<Int, VehicleItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VehicleItem> {
        val pageNumber = params.key ?: 1
        return when (val result = vehiclesRepository.getVehiclesInfo(pageNumber)) {
            is Error -> {
                LoadResult.Error(result.exception)
            }
            is Success -> {
                val vehiclesInfos = result.data.map {
                    VehicleItem(
                        it.name, it.make ?: "", it.model ?: "", it.imageUrl ?: "", it
                    )
                }
                val headerFields = result.headerFields
                val prevKey = headerFields?.currentPage?.minus(1)
                val prev = if (prevKey == 0) null else prevKey
                LoadResult.Page(vehiclesInfos, prev, headerFields?.nextPage())
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, VehicleItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }
}