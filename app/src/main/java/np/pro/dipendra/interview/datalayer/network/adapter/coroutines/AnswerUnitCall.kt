package np.pro.dipendra.interview.datalayer.network.adapter.coroutines

import np.pro.dipendra.interview.datalayer.network.models.exception.NoInternetException
import np.pro.dipendra.interview.datalayer.network.models.exception.UnknownException
import np.pro.dipendra.interview.datalayer.network.models.exception.parseResponseErrorBody
import np.pro.dipendra.interview.datalayer.repository.Answer
import np.pro.dipendra.interview.datalayer.repository.Answer.Error
import np.pro.dipendra.interview.datalayer.repository.Answer.Success
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

internal class AnswerUnitCall(
    private val delegate: Call<Unit>
) : Call<Answer<Unit>> {
    override fun enqueue(callback: Callback<Answer<Unit>>) {
        return delegate.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                val error = response.errorBody()
                if (response.isSuccessful) {

                    callback.onResponse(
                        this@AnswerUnitCall,
                        Response.success(Success(Unit, response.headers().toHeaderFields()))
                    )
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
                            this@AnswerUnitCall,
                            Response.success(Error(apiException))
                        )
                    } else {
                        callback.onResponse(
                            this@AnswerUnitCall,
                            Response.success(Error(UnknownException()))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<Unit>, throwable: Throwable) {
                val exception = when (throwable) {
                    is IOException -> NoInternetException(throwable)
                    else -> UnknownException(throwable)
                }
                callback.onResponse(this@AnswerUnitCall, Response.success(Error(exception)))
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = AnswerCall(delegate.clone())

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<Answer<Unit>> {
        throw UnsupportedOperationException("AnswerCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}

