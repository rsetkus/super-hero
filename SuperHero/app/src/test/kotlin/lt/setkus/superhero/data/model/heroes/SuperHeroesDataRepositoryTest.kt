package lt.setkus.superhero.data.model.heroes

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import lt.setkus.superhero.data.http.CharacterService
import lt.setkus.superhero.data.model.Image
import lt.setkus.superhero.data.model.extensions.getImageUrlString
import lt.setkus.superhero.domain.Result
import lt.setkus.superhero.domain.heroes.SuperHero
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.test.assertTrue

const val SUPER_HERO_NAME: String = "Mikas"
const val SUPER_HERO_DESCRIPTION = "description"
const val SUPER_HERO_ID = 123

class SuperHeroesDataRepositoryTest {

    private val image = Image("path", "jpg")
    private val listOfCharacters = listOf(Character(SUPER_HERO_ID, SUPER_HERO_NAME, SUPER_HERO_DESCRIPTION, image))
    private val exception = Exception("Houston we have a problem...")
    private val successfulService: CharacterService = FakeCharacterService(listOfCharacters)
    private val erroneousService: CharacterService = FakeCharacterService(exception)

    private lateinit var dataRepository: SuperHeroesDataRepository

    @Test
    fun `when service successfully returns result then should map SuperHeroes`() {
        dataRepository = SuperHeroesDataRepository(successfulService)
        runBlocking {
            val result = dataRepository.loadSuperHeroes() as Result.Success
            assertThat(result.data).contains(SuperHero(SUPER_HERO_ID, SUPER_HERO_NAME, SUPER_HERO_DESCRIPTION, "path.jpg")).hasSize(listOfCharacters
                .size)
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

    @Test
    fun `when error on loading one hero then should return error result`() {
        dataRepository = SuperHeroesDataRepository(erroneousService)
        runBlocking {
            val result = dataRepository.loadSuperHero(SUPER_HERO_ID)
            assertTrue(result is Result.Error)
        }
    }

    @Test
    fun `when hero successfully is returned than should map to SuperHero`() {
        dataRepository = SuperHeroesDataRepository(successfulService)
        runBlocking {
            val result = dataRepository.loadSuperHero(SUPER_HERO_ID) as Result.Success
            assertThat(result.data).isEqualTo(SuperHero(SUPER_HERO_ID, SUPER_HERO_NAME, SUPER_HERO_DESCRIPTION, image.getImageUrlString()))
        }
    }
}

private class FakeCharacterService(val superHeroes: List<Character>) : CharacterService {

    var exception: Throwable? = null

    override fun getCharacter(characterId: Int): Deferred<CharacterDataWrapper> {
        return makeAsycnResponse()
    }

    private fun makeAsycnResponse(): Deferred<CharacterDataWrapper> {
        return GlobalScope.async {
            if (exception == null) {
                CharacterDataWrapper(CharacterDataContainer(superHeroes))
            } else {
                throw Exception(exception)
            }
        }
    }

    constructor(exception: Throwable) : this(listOf()) {
        this.exception = exception
    }

    override fun getCharacters(): Deferred<CharacterDataWrapper> {
        return makeAsycnResponse()
    }
}