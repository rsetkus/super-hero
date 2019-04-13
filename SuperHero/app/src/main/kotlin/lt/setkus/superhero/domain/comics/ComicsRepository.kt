package lt.setkus.superhero.domain.comics

import lt.setkus.superhero.domain.Result

interface ComicsRepository {
    suspend fun loadComicsByHero(heroId: Int): Result<List<Comic>>
}