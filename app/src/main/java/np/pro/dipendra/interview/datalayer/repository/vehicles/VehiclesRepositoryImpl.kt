package np.pro.dipendra.interview.datalayer.repository.vehicles

import np.pro.dipendra.interview.datalayer.network.NetworkDataSource
import np.pro.dipendra.interview.datalayer.network.models.VehiclesApiModel
import np.pro.dipendra.interview.datalayer.repository.Answer
import np.pro.dipendra.interview.datalayer.repository.map
import javax.inject.Inject

class VehiclesRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
) : VehiclesRepository {

    override suspend fun getVehiclesInfo(
        pageNumber: Int,
        name: String?
    ): Answer<List<VehiclesInfo>> {
        return networkDataSource.getVehicles(pageNumber, name).map { apiModels, headerFields ->
            val list = apiModels.map { it.toVehiclesInfo() }
            Answer.Success(list, headerFields)
        }
    }

    private fun VehiclesApiModel.toVehiclesInfo(): VehiclesInfo {
        return VehiclesInfo(
            id = this.id,
            name = this.name,
            make = this.make,
            model = this.model,
            vehicleType = this.typeName,
            imageUrl = this.imageUrl,
            vin = this.vin,
            year = this.year
        )
    }
}
