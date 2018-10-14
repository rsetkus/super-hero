package lt.setkus.superhero.data.http

import kotlinx.coroutines.experimental.Deferred
import lt.setkus.superhero.data.heroes.CharacterDataWrapper
import retrofit2.http.GET

interface CharacterService {
    @GET("/v1/public/characters")
    fun getCharacters(): Deferred<CharacterDataWrapper>
}