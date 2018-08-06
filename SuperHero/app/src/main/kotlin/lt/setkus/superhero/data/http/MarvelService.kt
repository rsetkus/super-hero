package lt.setkus.superhero.data.http

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MarvelService {

    @GET("/v1/public/characters")
    fun getCharacters()

    companion object {
        fun create(): MarvelService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://gateway.marvel.com")
                    .build()

            return retrofit.create(MarvelService::class.java);
        }
    }
}