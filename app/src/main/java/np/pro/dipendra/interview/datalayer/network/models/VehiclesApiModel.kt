package np.pro.dipendra.interview.datalayer.network.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VehiclesApiModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("make") val make: String,
    @SerializedName("model") val model: String,
    @SerializedName("default_image_url_small") val imageUrl: String
) : Serializable