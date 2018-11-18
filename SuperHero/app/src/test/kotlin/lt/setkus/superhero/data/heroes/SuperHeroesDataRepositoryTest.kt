package lt.setkus.superhero.data.heroes

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import lt.setkus.superhero.data.http.CharacterService
import lt.setkus.superhero.domain.Result
import lt.setkus.superhero.domain.heroes.SuperHero
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.test.assertTrue

const val SUPER_HERO_NAME: String = "Mikas"

class SuperHeroesDataRepositoryTest {

    private val image = Image("path", "jpg")
    private val listOfCharacters = listOf(Character(SUPER_HERO_NAME, image))
    private val exception = Exception("Houston we have a problem...")
    private val successfulService: CharacterService = FakeCharacterService(listOfCharacters)
    private val erroneousService: CharacterService = FakeCharacterService(exception)

    private lateinit var dataRepository: SuperHeroesDataRepository

    @Test
    fun `when service successfully returns result then should map SuperHeroes`() {
        dataRepository = SuperHeroesDataRepository(successfulService)
        runBlocking {
            val result = dataRepository.loadSuperHeroes() as Result.Success
            assertThat(result.data).contains(SuperHero(SUPER_HERO_NAME, "path.jpg")).hasSize(listOfCharacters.size)
        }
    }

    @Test
    fun `when error occurs on loading super heroes then should return error result`() {
        dataRepository = SuperHeroesDataRepository(erroneousService)
        runBlocking {
            val result = dataRepository.loadSuperHeroes()
            assertTrue(result is Result.Error)
        }
    }
}

private class FakeCharacterService(val superHeroes: List<Character>) : CharacterService {
    var exception: Throwable? = null

    constructor(exception: Throwable) : this(listOf()) {
        this.exception = exception
    }

    override fun getCharacters(): Deferred<CharacterDataWrapper> {
        return GlobalScope.async {
            if (exception == null)
                CharacterDataWrapper(CharacterDataContainer(superHeroes))
            else
                throw Exception(exception)
        }
    }
}