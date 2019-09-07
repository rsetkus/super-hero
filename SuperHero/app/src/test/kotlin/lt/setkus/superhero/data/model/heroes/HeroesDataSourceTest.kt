package lt.setkus.superhero.data.model.heroes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PageKeyedDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import lt.setkus.superhero.app.heroes.SuperHeroViewData
import lt.setkus.superhero.data.http.CHARACTERS_LIMIT
import lt.setkus.superhero.data.http.NetworkState
import lt.setkus.superhero.domain.Result
import lt.setkus.superhero.domain.heroes.SuperHero
import lt.setkus.superhero.domain.heroes.SuperHeroesRepository
import lt.setkus.superhero.testObserver
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Rule
import org.junit.Test

class HeroesDataSourceTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val superHeroesRepository = mockk<SuperHeroesRepository>(relaxed = true)
    private val paramsInitial = mockk<PageKeyedDataSource.LoadInitialParams<Long>>()
    private val callbackInitial = mockk<PageKeyedDataSource.LoadInitialCallback<Long, SuperHeroViewData>>()
    private val callback = mockk<PageKeyedDataSource.LoadCallback<Long, SuperHeroViewData>>()
    private val mapper = mockk<(domain: SuperHero) -> SuperHeroViewData>()
    private val heroesDataSource by lazy {
        HeroesDataSource(Dispatchers.Unconfined, Dispatchers.Unconfined, superHeroesRepository, mapper)
    }

    private val successfulSuperHeroesResult = Result.Success(listOf<SuperHero>())
    private val errorSuperHeroesResult = Result.Error(Exception())

    @Test
    fun `when called initial loading then should offset should be zero`() {
        coEvery { superHeroesRepository.loadSuperHeroes(0) } returns successfulSuperHeroesResult

        with(heroesDataSource) {
            val testNetworkState = networkState.testObserver()
            loadInitial(paramsInitial, callbackInitial)

            coVerify { superHeroesRepository.loadSuperHeroes(0) }
            verify { callbackInitial.onResult(any(), 0, CHARACTERS_LIMIT) }
            assertThat(testNetworkState.observedValues)
                .containsOnly(NetworkState.LOADED, NetworkState.LOADING)
        }
    }

    @Test
    fun `when called initial loading and error occurred then no interaction with callback shouldn't appear`() {
        coEvery { superHeroesRepository.loadSuperHeroes(0) } returns errorSuperHeroesResult

        with(heroesDataSource) {
            val testNetworkState = networkState.testObserver()
            loadInitial(paramsInitial, callbackInitial)

            coVerify(exactly = 0) { callbackInitial.onResult(any(), any(), any()) }
            assertThat(testNetworkState.observedValues)
                .containsOnly(NetworkState.ERROR, NetworkState.LOADING)
        }
    }

    @Test
    fun `when loadAfter called then offset should be increased`() {
        val params = PageKeyedDataSource.LoadParams<Long>(20, 0)
        val expectedOffset = 20 + CHARACTERS_LIMIT
        coEvery { superHeroesRepository.loadSuperHeroes(expectedOffset) } returns successfulSuperHeroesResult

        with(heroesDataSource) {
            val testNetworkState = networkState.testObserver()
            loadAfter(params, callback)

            coVerify { superHeroesRepository.loadSuperHeroes(expectedOffset) }
            verify { callback.onResult(any(), expectedOffset) }
            assertThat(testNetworkState.observedValues)
                .containsOnly(NetworkState.LOADED, NetworkState.LOADING)
        }
    }

    @Test
    fun `when loadAfter called and error occurred then no interaction with callback shouldn't appear`() {
        val params = PageKeyedDataSource.LoadParams<Long>(20, 0)
        val expectedOffset = 20 + CHARACTERS_LIMIT
        coEvery { superHeroesRepository.loadSuperHeroes(expectedOffset) } returns errorSuperHeroesResult

        with(heroesDataSource) {
            val testNetworkState = networkState.testObserver()
            loadAfter(params, callback)

            coVerify(exactly = 0) { callback.onResult(any(), any()) }
            assertThat(testNetworkState.observedValues)
                .containsOnly(NetworkState.ERROR, NetworkState.LOADING)
        }
    }
}