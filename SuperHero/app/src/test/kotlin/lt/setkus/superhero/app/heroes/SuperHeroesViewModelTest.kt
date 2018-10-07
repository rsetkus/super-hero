package lt.setkus.superhero.app.heroes

import android.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import lt.setkus.superhero.domain.heroes.SuperHero
import lt.setkus.superhero.domain.heroes.SuperHeroesRepository
import lt.setkus.superhero.testObserver
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SuperHeroesViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val superHeroesRepository = mockk<SuperHeroesRepository>()
    private lateinit var superHeroesViewModel: SuperHeroesViewModel

    private val mockSuperHeroes = listOf(SuperHero("Iron Man"), SuperHero("Hulk"))

    @Before
    fun setUp() {
        every { superHeroesRepository.loadSuperHeroes() } returns mockSuperHeroes
        superHeroesViewModel = SuperHeroesViewModel(superHeroesRepository)
    }

    @Test
    fun `when tasks are received from repository then should load to the view`() {
        with(superHeroesViewModel) {
            loadSuperHeroes()
            verify { superHeroesRepository.loadSuperHeroes() }

            val receivedSuperHeroes = superHeroesLiveData.testObserver().observedValues.get(0)
            assertThat(receivedSuperHeroes).isNotEmpty().hasSize(mockSuperHeroes.size)
        }
    }
}