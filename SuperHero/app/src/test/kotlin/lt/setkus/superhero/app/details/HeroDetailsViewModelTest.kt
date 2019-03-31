package lt.setkus.superhero.app.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import lt.setkus.superhero.app.common.ViewState
import lt.setkus.superhero.domain.Result
import lt.setkus.superhero.domain.heroes.SuperHero
import lt.setkus.superhero.domain.heroes.SuperHeroesRepository
import lt.setkus.superhero.testObserver
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFalse

private const val SUPER_HERO_ID = 1
private const val SUPER_HERO_NAME = "Iron Man"
private const val SUPER_HERO_DESCRIPTION = "description"
private const val SUPER_HERO_IMAGE_URL = "imageUrl"

class HeroDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val superHeroesRepository = mockk<SuperHeroesRepository>(relaxed = true)

    private val superHero = SuperHero(SUPER_HERO_ID, SUPER_HERO_NAME, SUPER_HERO_DESCRIPTION, SUPER_HERO_IMAGE_URL)
    private val successfulResult = Result.Success(superHero)
    private val successfulViewState = ViewState.Success(HeroDetailsViewData(SUPER_HERO_NAME, SUPER_HERO_DESCRIPTION))

    private val error = Throwable("Error")
    private val errorResult = Result.Error(error)

    private lateinit var detailViewModel: HeroDetailsViewModel

    @Before
    fun setUp() {
        detailViewModel = HeroDetailsViewModel(Dispatchers.Unconfined, Dispatchers.Unconfined, superHeroesRepository)
    }

    @Test
    fun `when result received then should emit result to view`() {
        mockRepositoryLoadSuperHero()
        with(detailViewModel) {
            val testLiveData = liveData.testObserver()
            loadSuperhero(SUPER_HERO_ID)
            coVerify { superHeroesRepository.loadSuperHero(SUPER_HERO_ID) }

            val success = testLiveData.observedValues.get(1) as ViewState.Success<SuperHero>
            assertThat(success).isEqualTo(successfulViewState)
        }
    }

    private fun mockRepositoryLoadSuperHero() {
        coEvery { superHeroesRepository.loadSuperHero(SUPER_HERO_ID) } returns successfulResult
    }

    @Test
    fun `before loading hero should indicate loading state`() {
        with(detailViewModel) {
            val testLiveData = liveData.testObserver()
            loadSuperhero(SUPER_HERO_ID)
            val firstViewState = testLiveData.observedValues.first()
            assertThat(firstViewState).isInstanceOf(ViewState.Loading::class.java)
        }
    }

    @Test
    fun `after loading data should indicate finished state`() {
        mockRepositoryLoadSuperHero()
        with(detailViewModel) {
            val tesLiveData = liveData.testObserver()
            loadSuperhero(SUPER_HERO_ID)
            val lastReceivedViewState = tesLiveData.observedValues.last()
            assertThat(lastReceivedViewState).isInstanceOf(ViewState.Finished::class.java)
        }
    }

    @Test
    fun `when error on loading hero then should push error view state`() {
        coEvery { superHeroesRepository.loadSuperHero(SUPER_HERO_ID) } returns errorResult
        with(detailViewModel) {
            val testLiveData = liveData.testObserver()
            loadSuperhero(SUPER_HERO_ID)
            val lastReceivedViewState = testLiveData.observedValues.get(1)
            assertThat(lastReceivedViewState).isInstanceOf(ViewState.Error::class.java)
        }
    }

    @Test
    fun `when view model is cleared then should cancel coroutine`() {
        coEvery {
            superHeroesRepository.loadSuperHero(SUPER_HERO_ID)
        } coAnswers {
            delay(1000)
            successfulResult
        }

        detailViewModel.loadSuperhero(SUPER_HERO_ID)
        detailViewModel.onCleared()
        assertFalse(detailViewModel.job.isActive)
    }
}