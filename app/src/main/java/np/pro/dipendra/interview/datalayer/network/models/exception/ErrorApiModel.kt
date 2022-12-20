package np.pro.dipendra.interview.datalayer.network.models.exception

import com.google.gson.annotations.SerializedName

data class ErrorApiModel(
    @SerializedName("code")
    val code: Int? = null,

    @SerializedName("message")
    val message: String? = null
)