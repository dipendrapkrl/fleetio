package np.pro.dipendra.interview.datalayer.network

import np.pro.dipendra.interview.datalayer.network.models.VehiclesApiModel
import np.pro.dipendra.interview.datalayer.network.retrofit.VehiclesApi
import np.pro.dipendra.interview.datalayer.repository.Answer
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(private val vehiclesApi: VehiclesApi) :
    NetworkDataSource {
    override suspend fun getVehicles(
        pageNumber: Int,
        make: String?
    ): Answer<List<VehiclesApiModel>> {
        return vehiclesApi.getVehicles(pageNumber, make)
    }

}