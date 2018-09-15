package lt.setkus.superhero.data.http

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface MarvelService {

    companion object {
        private val retrofit: Retrofit = Retrofit.Builder()
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://gateway.marvel.com")
                .build()

        private fun getClient(): OkHttpClient {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            return OkHttpClient.Builder()
                    .addInterceptor { chain: Interceptor.Chain ->
                        val httpUrl = chain.request().url()
                                .newBuilder()
                                .build()
                        val request = chain.request().newBuilder().url(httpUrl).build()
                        chain.proceed(request)
                    }
                    .addInterceptor(httpLoggingInterceptor)
                    .build()
        }

        fun getCharacterService(): CharacterService = retrofit.create(CharacterService::class.java)
    }
}