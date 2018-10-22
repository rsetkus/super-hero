package lt.setkus.superhero.data.http

import lt.setkus.superhero.utils.getPrivateKey
import lt.setkus.superhero.utils.getPublicKey
import lt.setkus.superhero.utils.md5
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun provideOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

    val publicKey = getPublicKey()
    val privateKey = getPrivateKey()

    return OkHttpClient.Builder()
        .addInterceptor { chain: Interceptor.Chain ->
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
        .addInterceptor(httpLoggingInterceptor)
        .build()
}