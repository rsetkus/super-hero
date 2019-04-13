package lt.setkus.superhero.data.http

import kotlinx.coroutines.Deferred
import lt.setkus.superhero.data.model.comics.ComicDataWrapper
import retrofit2.http.GET
import retrofit2.http.Path

interface ComicsService {
    @GET("/v1/public/characters/{characterId}/comics")
    fun getComics(@Path("characterId") characterId: Int): Deferred<ComicDataWrapper>
}