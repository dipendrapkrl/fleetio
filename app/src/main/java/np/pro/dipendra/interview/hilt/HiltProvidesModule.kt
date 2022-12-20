package np.pro.dipendra.interview.hilt

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import np.pro.dipendra.interview.BuildConfig
import np.pro.dipendra.interview.datalayer.network.adapter.coroutines.AnswerCallAdapterFactory
import np.pro.dipendra.interview.datalayer.network.okhttp.OkhttpWrapper
import np.pro.dipendra.interview.datalayer.network.retrofit.VehiclesApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltProvidesModule {
    @Provides
    @Singleton
    fun providesPriceApi(okhttpWrapper: OkhttpWrapper): VehiclesApi {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okhttpWrapper.okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(AnswerCallAdapterFactory()).build()
            .create(VehiclesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor, flipperOkhttpInterceptor: FlipperOkhttpInterceptor
    ): OkhttpWrapper {
        return OkhttpWrapper(
            OkHttpClient().newBuilder()
                .addNetworkInterceptor(authInterceptor)
                .addNetworkInterceptor(flipperOkhttpInterceptor).build()
        )
    }

    @Provides
    @Singleton
    fun provideFlipperOkhttpInterceptor(networkFlipperPlugin: NetworkFlipperPlugin): FlipperOkhttpInterceptor {
        return FlipperOkhttpInterceptor(networkFlipperPlugin)
    }

    @Provides
    @Singleton
    fun provideNetworkFlipperPlugin(): NetworkFlipperPlugin {
        return NetworkFlipperPlugin()
    }


    @Provides
    @Singleton
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }


    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class IoDispatcher

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DefaultDispatcher

}