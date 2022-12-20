package np.pro.dipendra.interview.datalayer.network.adapter.coroutines

import np.pro.dipendra.interview.datalayer.repository.Answer
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class AnswerCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        // suspend functions wrap the response type in `Call`
        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        // check first that the return type is `ParameterizedType`
        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<Answer<<Foo>>"
        }

        // get the response type inside the `Call` type
        val responseType = getParameterUpperBound(0, returnType)
        // if the response type is not Answer then we can't handle this type, so we return null
        if (getRawType(responseType) != Answer::class.java) {
            return null
        }

        // the response type is ApiResponse and should be parameterized
        check(responseType is ParameterizedType) { "Response must be parameterized as Answer<Foo> or Answer<out Foo>" }

        val successBodyType = getParameterUpperBound(0, responseType)
        return if(successBodyType == Unit::class.java) {
            AnswerUnitCallAdapter(successBodyType)
        } else {
            AnswerCallAdapter<Any>(successBodyType)
        }
    }
}