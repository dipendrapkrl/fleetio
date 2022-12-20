package np.pro.dipendra.interview.datalayer.network.adapter.coroutines

import np.pro.dipendra.interview.datalayer.repository.HeaderFields
import okhttp3.Headers

fun Headers.toHeaderFields(): HeaderFields {
    val perPageLimit = this["X-Pagination-Limit"]?.toInt() ?: 0
    val currentPage = this["X-Pagination-Current-Page"]?.toInt() ?: 0
    //assuming there will be 1 page even if there are no items. TODO Verify
    val numOfPages = this["X-Pagination-Total-Pages"]?.toInt() ?: 1
    val numOfItems = this["X-Pagination-Total-Count"]?.toInt() ?: 0
    return HeaderFields(currentPage, perPageLimit, numOfPages, numOfItems)
}