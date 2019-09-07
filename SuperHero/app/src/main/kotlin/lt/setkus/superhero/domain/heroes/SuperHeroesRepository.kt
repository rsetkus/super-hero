package lt.setkus.superhero.domain.heroes

import lt.setkus.superhero.domain.Result

interface SuperHeroesRepository {
    suspend fun loadSuperHeroes(offset: Long): Result<List<SuperHero>>
    suspend fun loadSuperHero(heroId: Int): Result<SuperHero>
}