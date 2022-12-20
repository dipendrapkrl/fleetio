package np.pro.dipendra.interview.datalayer.network.adapter.coroutines

import np.pro.dipendra.interview.datalayer.repository.Answer
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * This is used for Answer<Unit>
 */
internal class AnswerUnitCallAdapter(
    private val responseType: Type,
) : CallAdapter<Unit, Call<Answer<Unit>>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<Unit>): Call<Answer<Unit>> {
        return AnswerUnitCall(call)
    }
}