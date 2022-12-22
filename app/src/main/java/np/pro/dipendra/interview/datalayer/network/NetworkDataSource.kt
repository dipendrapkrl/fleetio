package np.pro.dipendra.interview.datalayer.network

import np.pro.dipendra.interview.datalayer.network.models.VehiclesApiModel
import np.pro.dipendra.interview.datalayer.repository.Answer

interface NetworkDataSource {
    suspend fun getVehicles(pageNumber: Int, make: String?): Answer<List<VehiclesApiModel>>
}
