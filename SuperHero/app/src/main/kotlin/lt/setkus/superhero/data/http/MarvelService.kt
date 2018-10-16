package lt.setkus.superhero.data.http

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import lt.setkus.superhero.utils.getPrivateKey
import lt.setkus.superhero.utils.getPublicKey
import lt.setkus.superhero.utils.md5
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface MarvelService {

    companion object {
        private val publicKey = getPublicKey()
        private val privateKey = getPrivateKey()

        private val retrofit: Retrofit = Retrofit.Builder()
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl("https://gateway.marvel.com")
                .build()

        private fun getClient(): OkHttpClient {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            return OkHttpClient.Builder()
                    .addInterceptor { chain: Interceptor.Chain ->
                        val timeStamp = System.currentTimeMillis().toString()
                        val httpUrl = chain.request().url()
                                .newBuilder()
                                .addQueryParameter("ts", timeStamp)
                                .addQueryParameter("apikey", publicKey)
                                .addQueryParameter("hash", getHash(timeStamp))
                                .build()
                        val request = chain.request().newBuilder().url(httpUrl).build()
                        chain.proceed(request)
                    }
                    .addInterceptor(httpLoggingInterceptor)
                    .build()
        }

        private fun getHash(timeStamp: String): String = timeStamp.plus(privateKey).plus(publicKey).md5()

        fun getCharacterService(): CharacterService = retrofit.create(CharacterService::class.java)
    }
}