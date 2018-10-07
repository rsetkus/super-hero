package lt.setkus.superhero.app.heroes

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import lt.setkus.superhero.domain.heroes.SuperHero
import lt.setkus.superhero.domain.heroes.SuperHeroesRepository
import org.junit.Before
import org.junit.Test

class SuperHeroesViewModelTest {

    private val superHeroesRepository = mockk<SuperHeroesRepository>()
    private lateinit var superHeroesViewModel: SuperHeroesViewModel

    private val superHeroes = listOf(SuperHero("Iron Man"), SuperHero("Hulk"))

    @Before
    fun setUp() {
        every { superHeroesRepository.loadSuperHeroes() } returns superHeroes

        superHeroesViewModel = SuperHeroesViewModel(superHeroesRepository)
    }

    @Test
    fun `when tasks are received from repository then should load to the view`() {
        with(superHeroesViewModel) {
            loadSuperHeroes()
            verify { superHeroesRepository.loadSuperHeroes() }
        }
    }
}