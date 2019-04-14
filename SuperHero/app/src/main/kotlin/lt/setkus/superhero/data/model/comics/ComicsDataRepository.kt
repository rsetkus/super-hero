package lt.setkus.superhero.data.model.comics

import lt.setkus.superhero.data.http.ComicsService
import lt.setkus.superhero.data.model.extensions.getImageUrlString
import lt.setkus.superhero.domain.comics.Comic as ComicDomain
import lt.setkus.superhero.domain.comics.ComicsRepository
import lt.setkus.superhero.domain.Result
import lt.setkus.superhero.utils.awaitResult

private val comicMapper = { comic: Comic ->
    ComicDomain(
        comic.id,
        comic.title,
        comic.description,
        comic.thumbnail?.getImageUrlString()
    )
}

class ComicsDataRepository(
    val service: ComicsService
) : ComicsRepository {

    override suspend fun loadComicsByHero(heroid: Int): Result<List<ComicDomain>> {
        val result = service.getComics(heroid).awaitResult()
        return when (result) {
            is Result.Success -> mapResultToComics(result.data)
            is Result.Error -> Result.Error(result.exception)
        }
    }

    private fun mapResultToComics(data: ComicDataWrapper): Result<List<ComicDomain>> {
        val comics = data.comicDataContainer?.comics?.map { comicMapper(it) } ?: listOf()
        return Result.Success(comics)
    }
}