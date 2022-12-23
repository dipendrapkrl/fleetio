package np.pro.dipendra.interview.uilayer.vehiclelist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import np.pro.dipendra.interview.datalayer.repository.Answer.Error
import np.pro.dipendra.interview.datalayer.repository.Answer.Success
import np.pro.dipendra.interview.datalayer.repository.vehicles.VehiclesRepository

class VehicleListPagedSource constructor(
    private val vehiclesRepository: VehiclesRepository, private val searchKey: String?
) : PagingSource<Int, VehicleItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VehicleItem> {
        val pageNumber = params.key ?: 1
        return when (val result = vehiclesRepository.getVehiclesInfo(pageNumber, searchKey)) {
            is Error -> {
                LoadResult.Error(result.exception)
            }
            is Success -> {
                val vehiclesInfos = result.data.map {
                    val makeAndModel = when {
                        !it.make.isNullOrEmpty() && !it.model.isNullOrEmpty() -> "${it.model} by ${it.make}"
                        !it.make.isNullOrEmpty() && it.model.isNullOrEmpty() -> "${it.make}"
                        it.make.isNullOrEmpty() && !it.model.isNullOrEmpty() -> "${it.model}"
                        else -> ""
                    }

                    VehicleItem(
                        name = it.name,
                        makeAndModel = makeAndModel,
                        extra = it.vehicleType ?: "",
                        imageUrl = it.imageUrl ?: "",
                        year = it.year ?: "",
                        vin = it.vin ?: "",
                        vehiclesInfo = it
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