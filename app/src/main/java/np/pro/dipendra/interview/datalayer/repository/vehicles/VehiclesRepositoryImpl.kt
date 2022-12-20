package np.pro.dipendra.interview.datalayer.repository.vehicles

import np.pro.dipendra.interview.datalayer.network.NetworkDataSource
import np.pro.dipendra.interview.datalayer.network.models.VehiclesApiModel
import np.pro.dipendra.interview.datalayer.repository.Answer
import np.pro.dipendra.interview.datalayer.repository.map
import javax.inject.Inject

class VehiclesRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
) : VehiclesRepository {

    override suspend fun getVehiclesInfo(pageNumber: Int): Answer<List<VehiclesInfo>> {
        return networkDataSource.getVehicles(pageNumber).map { apiModels, headerFields ->
            val list = apiModels.map { it.toVehiclesInfo() }.take(4)
            Answer.Success(list, headerFields)
        }
    }

    private fun VehiclesApiModel.toVehiclesInfo(): VehiclesInfo {
        return VehiclesInfo(this.id, this.name, this.make, this.model, this.imageUrl)
    }
}
