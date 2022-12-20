package np.pro.dipendra.interview.datalayer.network.models.exception

sealed class NetworkException(
    message: String = "",
    cause: Throwable? = null
) : ApplicationException(message, cause)

class NoInternetException(cause: Throwable? = null) : NetworkException("No internet exception", cause)

class TimeoutException(cause: Throwable? = null) : NetworkException("Timeout exception", cause)

class UnknownNetworkException(cause: Throwable? = null) : NetworkException("Unknown network exception", cause)

open class ParseException(
    message: String = "",
    cause: Throwable? = null
) : NetworkException(message, cause)