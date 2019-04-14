package lt.setkus.superhero.app.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import lt.setkus.superhero.app.common.ViewState
import lt.setkus.superhero.domain.Result
import lt.setkus.superhero.domain.comics.Comic
import lt.setkus.superhero.domain.comics.ComicsRepository
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

private const val COMIC_ID = 2
private const val COMIC_TITLE = "Avengers"
private const val COMIC_THUMBNAIL_URL = "avengers.url"

class HeroDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val superHeroesRepository = mockk<SuperHeroesRepository>(relaxed = true)
    private val comicsRepository = mockk<ComicsRepository>()

    private val superHero = SuperHero(SUPER_HERO_ID, SUPER_HERO_NAME, SUPER_HERO_DESCRIPTION, SUPER_HERO_IMAGE_URL)
    private val successfulResult = Result.Success(superHero)
    private val successfulViewState = ViewState.Success(HeroDetailsViewData(SUPER_HERO_NAME, SUPER_HERO_DESCRIPTION))

    private val comics = listOf(Comic(COMIC_ID, COMIC_TITLE, "", COMIC_THUMBNAIL_URL))
    private val comicsSuccessfulResult = Result.Success(comics)
    private val comicsSuccessfulViewState = ViewState.Success(listOf(HeroComicsViewData(COMIC_ID, COMIC_TITLE, COMIC_THUMBNAIL_URL)))

    private val error = Throwable("Error")
    private val errorResult = Result.Error(error)

    private lateinit var detailViewModel: HeroDetailsViewModel

    @Before
    fun setUp() {
        detailViewModel = HeroDetailsViewModel(Dispatchers.Unconfined, Dispatchers.Unconfined, superHeroesRepository, comicsRepository)
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
    fun `when comic result received then should emit data to view`() {
        mockSuccessfulComicsLoad()
        with(detailViewModel) {
            val testLiveData = liveData.testObserver()
            loadSuperHeroComics(SUPER_HERO_ID)
            coVerify { comicsRepository.loadComicsByHero(SUPER_HERO_ID) }

            val success = testLiveData.observedValues.get(1) as ViewState.Success<List<HeroComicsViewData>>
            assertThat(success).isEqualTo(comicsSuccessfulViewState)
        }
    }

    private fun mockSuccessfulComicsLoad() {
        coEvery { comicsRepository.loadComicsByHero(SUPER_HERO_ID) } returns comicsSuccessfulResult
    }

    @Test
    fun `before loading hero comics should indicate loading state`() {
        mockSuccessfulComicsLoad()
        with(detailViewModel) {
            val testLiveData = liveData.testObserver()
            loadSuperHeroComics(SUPER_HERO_ID)
            val firstViewState = testLiveData.observedValues.first()
            assertThat(firstViewState).isInstanceOf(ViewState.Loading::class.java)
        }
    }

    @Test
    fun `before loading comics should indicate loading state`() {
        mockSuccessfulComicsLoad()
        with(detailViewModel) {
            val testLiveData = liveData.testObserver()
            loadSuperHeroComics(SUPER_HERO_ID)
            val firstViewState = testLiveData.observedValues.first()
            assertThat(firstViewState).isInstanceOf(ViewState.Loading::class.java)
        }
    }

    @Test
    fun `after loading data should indicate finished state`() {
        mockRepositoryLoadSuperHero()
        with(detailViewModel) {
            val testLiveData = liveData.testObserver()
            loadSuperhero(SUPER_HERO_ID)
            val lastReceivedViewState = testLiveData.observedValues.last()
            assertThat(lastReceivedViewState).isInstanceOf(ViewState.Finished::class.java)
        }
    }

    @Test
    fun `after loading comics should emit finished view state`() {
        mockSuccessfulComicsLoad()
        with(detailViewModel) {
            val testLiveData = liveData.testObserver()
            loadSuperHeroComics(SUPER_HERO_ID)
            val lastReceivedViewState = testLiveData.observedValues.last()
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
    fun `when error on loading comics then should emit error view state`() {
        coEvery { comicsRepository.loadComicsByHero(SUPER_HERO_ID) } returns errorResult
        with(detailViewModel) {
            val testLiveData = liveData.testObserver()
            loadSuperHeroComics(SUPER_HERO_ID)
            val expectedErrorState = testLiveData.observedValues.get(1)
            assertThat(expectedErrorState).isInstanceOf(ViewState.Error::class.java)
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
        assertFalse(detailViewModel.superHereJob.isActive)
    }
}