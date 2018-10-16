package lt.setkus.superhero.domain.heroes

import lt.setkus.superhero.domain.Result

interface SuperHeroesRepository {
    suspend fun loadSuperHeroes(): Result<List<SuperHero>>
}