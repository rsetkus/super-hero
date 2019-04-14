package lt.setkus.superhero.app.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lt.setkus.superhero.app.common.ViewState
import lt.setkus.superhero.domain.Result
import lt.setkus.superhero.domain.comics.Comic
import lt.setkus.superhero.domain.comics.ComicsRepository
import lt.setkus.superhero.domain.heroes.SuperHeroesRepository

class HeroDetailsViewModel(
    val uiContext: CoroutineDispatcher,
    val backgroundContext: CoroutineDispatcher,
    val superHeroesRepository: SuperHeroesRepository,
    val comicsRepository: ComicsRepository
) : ViewModel() {

    internal val heroLiveData = MutableLiveData<ViewState>()
    internal val comicsLiveData = MutableLiveData<ViewState>()
    internal lateinit var superHereJob: Job
    internal lateinit var comicsJob: Job

    fun loadSuperhero(heroId: Int) {
        superHereJob = GlobalScope.launch(uiContext) {
            heroLiveData.value = ViewState.Loading()
            val result = withContext(backgroundContext) { superHeroesRepository.loadSuperHero(heroId) }
            when (result) {
                is Result.Success -> heroLiveData.value = ViewState.Success(HeroDetailsViewData(result.data.name, result.data.description))
                is Result.Error -> heroLiveData.value = ViewState.Error(result.exception)
            }

            heroLiveData.value = ViewState.Finished()
        }
    }

    public override fun onCleared() {
        super.onCleared()
        superHereJob.cancel()
    }

    fun loadSuperHeroComics(heroId: Int) {
        comicsJob = GlobalScope.launch(uiContext) {
            comicsLiveData.value = ViewState.Loading()
            val result = withContext(backgroundContext) { comicsRepository.loadComicsByHero(heroId) }
            when (result) {
                is Result.Success -> comicsLiveData.value = ViewState.Success(mapResultToHeroComics(result.data))
                is Result.Error -> comicsLiveData.value = ViewState.Error(result.exception)
            }

            comicsLiveData.value = ViewState.Finished()
        }
    }

    private fun mapResultToHeroComics(data: List<Comic>): List<HeroComicsViewData> {
        return data.map { HeroComicsViewData(it.id, it.title ?: "No title", it.imageUrl) }
    }
}
