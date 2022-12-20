package np.pro.dipendra.interview.datalayer.network.models.exception

class UnknownException(cause: Throwable? = null) : ApplicationException("Unknown exception", cause)