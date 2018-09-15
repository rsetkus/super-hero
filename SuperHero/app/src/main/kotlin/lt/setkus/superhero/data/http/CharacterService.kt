package lt.setkus.superhero.data.http

import lt.setkus.superhero.data.model.CharacterDataWrapper
import retrofit2.Call
import retrofit2.http.GET

interface CharacterService {
    @GET("/v1/public/characters")
    fun getCharacters(): Call<CharacterDataWrapper>
}