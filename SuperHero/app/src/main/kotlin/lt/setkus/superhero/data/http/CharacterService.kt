package lt.setkus.superhero.data.http

import kotlinx.coroutines.Deferred
import lt.setkus.superhero.data.model.heroes.CharacterDataWrapper
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {
    @GET("/v1/public/characters")
    fun getCharacters(): Deferred<CharacterDataWrapper>

    @GET("/v1/public/characters/{characterId}")
    fun getCharacter(@Path("characterId") characterId: Int): Deferred<CharacterDataWrapper>
}