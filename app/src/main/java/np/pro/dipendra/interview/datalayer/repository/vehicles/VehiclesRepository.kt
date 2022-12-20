package np.pro.dipendra.interview.datalayer.repository.vehicles

import np.pro.dipendra.interview.datalayer.repository.Answer

interface VehiclesRepository {
    suspend fun getVehiclesInfo(pageNumber: Int): Answer<List<VehiclesInfo>>
}
