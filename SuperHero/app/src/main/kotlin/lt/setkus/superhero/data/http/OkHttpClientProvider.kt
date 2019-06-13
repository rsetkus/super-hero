package lt.setkus.superhero.data.http

import android.content.Context
import lt.setkus.superhero.utils.getPrivateKey
import lt.setkus.superhero.utils.getPublicKey
import lt.setkus.superhero.utils.isNetworkAvailable
import lt.setkus.superhero.utils.md5
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

private const val CACHE_SIZE = (10 * 1024 * 1024).toLong() // 10mb
private const val CACHE_ALIVE_TIME = 60 * 5 // 5min

object OkHttpClientProvider {

    @Volatile
    private var INSTANCE: OkHttpClient? = null

    fun getClient(context: Context): OkHttpClient {
        return INSTANCE ?: synchronized(this) {
            buildClient(context).also {
                INSTANCE = it
            }
        }
    }

    private fun buildClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(Cache(context.cacheDir, CACHE_SIZE))
            .addNetworkInterceptor(provideRewriteResponseInterceptor())
            .addInterceptor(provideResponseInterceptorOffline(context))
            .addInterceptor(provideApiKeyInterceptor())
            .addInterceptor(provideHttpLoggingInterceptor())
            .build()
    }

    private fun provideHttpLoggingInterceptor(): Interceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        return httpLoggingInterceptor
    }

    private fun provideResponseInterceptorOffline(context: Context): (Interceptor.Chain) -> Response {
        return { chain ->
            val request = chain.request()
            if (context.isNetworkAvailable()) {
                request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK)
            } else {
                request.newBuilder().cacheControl(CacheControl.FORCE_CACHE)
            }
            chain.proceed(request)
        }
    }

    private fun provideRewriteResponseInterceptor(): (Interceptor.Chain) -> Response {
        return { chain ->
            val cache = chain.request().header("cache")
            val originalResponse = chain.proceed(chain.request())
            val cacheControl = originalResponse.header("Cache-Control")
            if (cacheControl.isNullOrEmpty()) {
                val newResponse = originalResponse.newBuilder()
                if (cache.isNullOrEmpty()) {
                    newResponse.header("Cache-Control", "public, max-age=$CACHE_ALIVE_TIME").build()
                } else {
                    newResponse.build()
                }
            } else {
                originalResponse
            }
        }
    }

    private fun provideApiKeyInterceptor(): (Interceptor.Chain) -> Response {
        return { chain ->
            val publicKey = getPublicKey()
            val privateKey = getPrivateKey()

            val timeStamp = System.currentTimeMillis().toString()
            val hash = timeStamp.plus(privateKey).plus(publicKey).md5()
            val httpUrl = chain.request().url()
                .newBuilder()
                .addQueryParameter("ts", timeStamp)
                .addQueryParameter("apikey", publicKey)
                .addQueryParameter("hash", hash)
                .build()
            val request = chain.request().newBuilder().url(httpUrl).build()
            chain.proceed(request)
        }
    }
}