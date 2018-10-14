package lt.setkus.superhero.data.heroes

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import lt.setkus.superhero.data.http.CharacterService
import org.junit.Before
import org.junit.Test
import retrofit2.Call

class SuperHeroesDataRepositoryTest {

    private val service = mockk<CharacterService>()
    private val call = mockk<Call<CharacterDataWrapper>>(relaxed = true)
git
    private lateinit var dataRepository: SuperHeroesDataRepository

    @Before
    fun setUp() {
        every { service.getCharacters() } returns call
        dataRepository = SuperHeroesDataRepository(service)
    }

    @Test
    fun `when requested to load super heroes when should call character service method`() {
        dataRepository.loadSuperHeroes()
        verify { service.getCharacters() }
    }
}