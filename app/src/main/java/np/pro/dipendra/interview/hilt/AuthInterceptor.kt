package np.pro.dipendra.interview.hilt

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Account-Token", "798819214b")
            .addHeader("Authorization", "Token token=a3ddc620b35b609682192c167de1b1f3f5100387")
            .build()
        return chain.proceed(request)
    }

}
