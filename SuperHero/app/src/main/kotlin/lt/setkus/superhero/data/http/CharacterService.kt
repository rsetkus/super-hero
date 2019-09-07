package lt.setkus.superhero.data.http

import kotlinx.coroutines.Deferred
import lt.setkus.superhero.data.model.heroes.CharacterDataWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val CHARACTERS_LIMIT: Long = 20

interface CharacterService {
    @GET("/v1/public/characters")
    fun getCharacters(@Query("offset") offset: Long): Deferred<CharacterDataWrapper>

    @GET("/v1/public/characters/{characterId}")
    fun getCharacter(@Path("characterId") characterId: Int): Deferred<CharacterDataWrapper>
}