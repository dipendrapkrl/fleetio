package np.pro.dipendra.interview.datalayer.network.adapter.coroutines

import np.pro.dipendra.interview.datalayer.network.models.exception.NoInternetException
import np.pro.dipendra.interview.datalayer.network.models.exception.UnknownException
import np.pro.dipendra.interview.datalayer.network.models.exception.parseResponseErrorBody
import np.pro.dipendra.interview.datalayer.repository.Answer
import okhttp3.Request
import okhttp3.internal.toHeaderList
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

internal class AnswerCall<T : Any>(
    private val delegate: Call<T>
) : Call<Answer<T>> {
    override fun enqueue(callback: Callback<Answer<T>>) {
        return delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@AnswerCall,
                            Response.success(Answer.Success(body,response.headers().toHeaderFields()))
                        )
                    } else {
//                        Timber.e("You got a success, but an empty response body back, should use Result<Unit>")
                        callback.onResponse(
                            this@AnswerCall,
                            Response.success(Answer.Error(UnknownException()))
                        )
                    }
                } else {
                    val apiException = when {
                        error == null -> null
                        error.contentLength() == 0L -> null
                        else -> try {
                            parseResponseErrorBody(response)
                        } catch (ex: Exception) {
                            null
                        }
                    }
                    if (apiException != null) {
                        callback.onResponse(
                            this@AnswerCall,
                            Response.success(Answer.Error(apiException))
                        )
                    } else {
                        callback.onResponse(
                            this@AnswerCall,
                            Response.success(Answer.Error(UnknownException()))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                val exception = when (throwable) {
                    is IOException -> NoInternetException(throwable)
                    else -> UnknownException(throwable)
                }
                callback.onResponse(this@AnswerCall, Response.success(Answer.Error(exception)))
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = AnswerCall(delegate.clone())

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<Answer<T>> {
        throw UnsupportedOperationException("ResultCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}