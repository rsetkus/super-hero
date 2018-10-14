package lt.setkus.superhero.data.heroes

import lt.setkus.superhero.data.http.CharacterService
import lt.setkus.superhero.domain.Result
import lt.setkus.superhero.domain.heroes.SuperHero
import lt.setkus.superhero.domain.heroes.SuperHeroesRepository
import lt.setkus.superhero.utils.awaitResult

class SuperHeroesDataRepository(
    val service: CharacterService
) : SuperHeroesRepository {

    override suspend fun loadSuperHeroes(): Result<List<SuperHero>> {
        val result = service.getCharacters().awaitResult()
        return when (result) {
            is Result.Error -> result
            is Result.Success -> flatMap(result.data)
        }
    }

    private fun flatMap(wrapper: CharacterDataWrapper?): Result<List<SuperHero>> {
        val superHeroes = wrapper?.data?.results?.map { SuperHero("") } ?: listOf()
        return Result.Success(superHeroes)
    }
}