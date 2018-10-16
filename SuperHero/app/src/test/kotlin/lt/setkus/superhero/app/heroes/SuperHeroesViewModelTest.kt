package lt.setkus.superhero.app.heroes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.experimental.Dispatchers
import lt.setkus.superhero.app.common.ViewState
import lt.setkus.superhero.domain.Result
import lt.setkus.superhero.domain.heroes.SuperHero
import lt.setkus.superhero.domain.heroes.SuperHeroesRepository
import lt.setkus.superhero.testObserver
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class SuperHeroesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val superHeroesRepository = mockk<SuperHeroesRepository>(relaxed = true)
    private lateinit var superHeroesViewModel: SuperHeroesViewModel

    private val listOfSuperHeroes = listOf(SuperHero("Iron Man"), SuperHero("Hulk"))
    private val successfulSuperHeroesResult = Result.Success(listOfSuperHeroes)

    @Before
    fun setUp() {
        superHeroesViewModel = SuperHeroesViewModel(Dispatchers.Unconfined, Dispatchers.Unconfined, superHeroesRepository)
    }

    @Test
    fun `when tasks are received from repository then should load to the view`() {
        coEvery { superHeroesRepository.loadSuperHeroes() } returns successfulSuperHeroesResult
        with(superHeroesViewModel) {
            val testLiveData = superHeroesLiveData.testObserver()
            loadSuperHeroes()
            coVerify { superHeroesRepository.loadSuperHeroes() }

            val viewStateSuccess = testLiveData.observedValues.get(1) as ViewState.Success<List<SuperHero>>
            assertThat(viewStateSuccess.data).hasSize(listOfSuperHeroes.size)
        }
    }

    @Test
    fun `before loading super heroes should indicate loading state`() {
        with(superHeroesViewModel) {
            val testLiveData = superHeroesLiveData.testObserver()
            loadSuperHeroes()
            val receivedState = testLiveData.observedValues.get(0)
            assertTrue(receivedState is ViewState.Loading)
        }
    }

    @Test
    fun `after successfuly load should indicate finished state`() {
        coEvery { superHeroesRepository.loadSuperHeroes() } returns successfulSuperHeroesResult
        with(superHeroesViewModel) {
            val testLiveData = superHeroesLiveData.testObserver()
            loadSuperHeroes()
            val receivedState = testLiveData.observedValues.get(2)
            assertTrue(receivedState is ViewState.Finished)
        }
    }
}