package lt.setkus.superhero.data.model.heroes

import lt.setkus.superhero.data.http.CharacterService
import lt.setkus.superhero.data.model.extensions.getImageUrlString
import lt.setkus.superhero.domain.Result
import lt.setkus.superhero.domain.heroes.SuperHero
import lt.setkus.superhero.domain.heroes.SuperHeroesRepository
import lt.setkus.superhero.utils.awaitResult

private val mapCharacterToSuperHero = { character: Character? ->
    SuperHero(character?.name ?: "no name", character?.thumbnail?.getImageUrlString())
}

class SuperHeroesDataRepository(
    val service: CharacterService
) : SuperHeroesRepository {

    override suspend fun loadSuperHero(heroId: Int): Result<SuperHero> {
        val result = service.getCharacter(heroId).awaitResult()
        return when (result) {
            is Result.Error -> result
            is Result.Success -> mapFirstCharacterToSuperhero(result.data)
        }
    }

    override suspend fun loadSuperHeroes(): Result<List<SuperHero>> {
        val result = service.getCharacters().awaitResult()
        return when (result) {
            is Result.Error -> result
            is Result.Success -> mapCharactersToSuperHeroes(result.data)
        }
    }

    private fun mapFirstCharacterToSuperhero(wrapper: CharacterDataWrapper?): Result<SuperHero> {
        val superHero = mapCharacterToSuperHero(wrapper?.data?.results?.first())
        return Result.Success(superHero)
    }

    private fun mapCharactersToSuperHeroes(wrapper: CharacterDataWrapper?): Result<List<SuperHero>> {
        val superHeroes = wrapper?.data?.results?.map { mapCharacterToSuperHero(it) } ?: listOf()
        return Result.Success(superHeroes)
    }
}