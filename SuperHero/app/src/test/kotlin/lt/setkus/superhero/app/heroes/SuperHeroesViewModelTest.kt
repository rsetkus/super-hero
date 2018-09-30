package lt.setkus.superhero.app.heroes

import android.arch.core.executor.testing.InstantTaskExecutorRule
import lt.setkus.superhero.domain.heroes.SuperHeroesRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SuperHeroesViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var superHeroesRepository: SuperHeroesRepository

    private lateinit var superHeroesViewModel: SuperHeroesViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        superHeroesViewModel = SuperHeroesViewModel(superHeroesRepository)
    }

    @Test
    fun `when tasks loaded from repository then should delegate to view callback`() {
        with(superHeroesViewModel) {
            loadSuperHeroes()
            verify(superHeroesRepository).loadSuperHeroes()
        }
    }
}