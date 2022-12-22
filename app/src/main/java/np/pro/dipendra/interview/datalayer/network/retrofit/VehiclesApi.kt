package np.pro.dipendra.interview.datalayer.network.retrofit

import np.pro.dipendra.interview.datalayer.network.models.VehiclesApiModel
import np.pro.dipendra.interview.datalayer.repository.Answer
import retrofit2.http.GET
import retrofit2.http.Query

interface VehiclesApi {
    @GET("vehicles")
    suspend fun getVehicles(
        @Query("page") page: Int,
        @Query("q[make_eq]") make: String?
    ): Answer<List<VehiclesApiModel>>
}