package lt.setkus.superhero.data.heroes

import lt.setkus.superhero.data.http.MarvelService
import lt.setkus.superhero.domain.Result
import lt.setkus.superhero.domain.heroes.SuperHero
import lt.setkus.superhero.domain.heroes.SuperHeroesRepository
import lt.setkus.superhero.utils.awaitResult

class SuperHeroesDataRepository(
    val service: MarvelService
) : SuperHeroesRepository {

    override suspend fun loadSuperHeroes(): Result<List<SuperHero>> {
        val result = service.getCharacterService().getCharacters().awaitResult()
        return when (result) {
            is Result.Error -> result
            is Result.Success -> flatMap(result.data)
        }
    }

    private fun flatMap(wrapper: CharacterDataWrapper?): Result<List<SuperHero>> {
        val superHeroes = wrapper?.data?.results?.map { SuperHero(it.name ?: "no name") } ?: listOf()
        return Result.Success(superHeroes)
    }
}