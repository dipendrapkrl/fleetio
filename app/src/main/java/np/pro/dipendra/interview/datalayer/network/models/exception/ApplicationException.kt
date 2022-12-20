package np.pro.dipendra.interview.datalayer.network.models.exception

open class ApplicationException(msg: String, cause: Throwable? = null) :
    RuntimeException(msg, cause)