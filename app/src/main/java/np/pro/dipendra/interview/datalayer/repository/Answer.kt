package np.pro.dipendra.interview.datalayer.repository

import np.pro.dipendra.interview.datalayer.network.models.exception.ApplicationException

sealed class Answer<out T : Any> {
    data class Success<out T : Any>(val data: T, val headerFields: HeaderFields? = null) :
        Answer<T>()

    data class Error(val exception: ApplicationException) : Answer<Nothing>()
}

data class HeaderFields(
    val currentPage: Int,//default is 1
    val perPageLimit: Int,
    val noOfPages: Int,
    val totalItems: Int
) {
    fun nextPage(): Int? {
        return if (noOfPages > currentPage) {
            currentPage + 1
        } else null
    }
}

inline fun <R : Any, T : Any> Answer<T>.map(transform: (value: T, headerFields: HeaderFields?) -> Answer<R>): Answer<R> {
    return when (this) {
        is Answer.Success -> transform(this.data, this.headerFields)
        is Answer.Error -> this
    }
}