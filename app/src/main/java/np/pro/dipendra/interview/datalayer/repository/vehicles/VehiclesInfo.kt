package np.pro.dipendra.interview.datalayer.repository.vehicles

import java.io.Serializable

data class VehiclesInfo(
    val id: Int,
    val name: String,
    val make: String?,
    val model: String?,
    val imageUrl: String?
) : Serializable
