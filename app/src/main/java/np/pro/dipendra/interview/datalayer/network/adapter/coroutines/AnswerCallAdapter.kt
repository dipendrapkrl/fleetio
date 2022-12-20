package np.pro.dipendra.interview.datalayer.network.adapter.coroutines

import np.pro.dipendra.interview.datalayer.repository.Answer
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * This is used for Answer<Any> other than Answer<Unit>.
 */
internal class AnswerCallAdapter<T : Any>(
    private val responseType: Type,
) : CallAdapter<T, Call<Answer<T>>> {

    override fun responseType(): Type {
        return responseType
    }


    override fun adapt(call: Call<T>): Call<Answer<T>> {
        return AnswerCall(call)
    }
}