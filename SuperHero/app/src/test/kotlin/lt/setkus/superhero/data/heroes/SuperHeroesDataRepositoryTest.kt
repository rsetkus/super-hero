package lt.setkus.superhero.data.heroes

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.runBlocking
import lt.setkus.superhero.data.http.CharacterService
import lt.setkus.superhero.domain.Result
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class SuperHeroesDataRepositoryTest {

    private val service = mockk<CharacterService>()
    private val deferred = mockk<Deferred<CharacterDataWrapper>>(relaxed = true)

    private lateinit var dataRepository: SuperHeroesDataRepository
    private val error = Exception("Any error")

    @Before
    fun setUp() {
        every { service.getCharacters() } returns deferred
        dataRepository = SuperHeroesDataRepository(service)
    }

    @Test
    fun `when requested to load super heroes when should call character service method`() {
        runBlocking {
            dataRepository.loadSuperHeroes()
            verify { service.getCharacters() }
        }
    }

    @Test
    fun `when error occurs on loading super heroes then should return error result`() {
        runBlocking {
            assertThat(dataRepository.loadSuperHeroes()).isEqualTo(Result.Error(error))
        }
    }
}