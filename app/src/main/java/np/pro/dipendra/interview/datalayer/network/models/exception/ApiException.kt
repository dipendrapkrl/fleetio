package np.pro.dipendra.interview.datalayer.network.models.exception

import com.google.gson.Gson
import retrofit2.Response

open class ApiException(
    val responseCode: Int,
    message: String = "",
    cause: Throwable? = null,
) : ApplicationException(message, cause)

object NotFoundException : ApiException(404, "Not found")
class NotAuthException(message: String) : ApiException(401, message)

class ApiMessagesException(responseCode: Int, message: String, cause: Throwable? = null) :
    ApiException(responseCode, message, cause)

fun parseResponseErrorBody(response: Response<*>?): ApiException? {
    return response?.errorBody()?.string()?.let {
        val errorResponse = Gson().fromJson(it, ErrorApiModel::class.java)
        val msg = errorResponse?.message ?: ""

        when (val code = response.code()) {
            401 -> NotAuthException(msg)
            404 -> NotFoundException
            else -> ApiMessagesException(code, msg)
        }
    }
}

