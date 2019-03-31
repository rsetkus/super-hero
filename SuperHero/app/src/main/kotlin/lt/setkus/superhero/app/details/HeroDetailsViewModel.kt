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
import lt.setkus.superhero.domain.heroes.SuperHeroesRepository

class HeroDetailsViewModel(
    val uiContext: CoroutineDispatcher,
    val backgroundContext: CoroutineDispatcher,
    val repository: SuperHeroesRepository
) : ViewModel() {

    internal val liveData = MutableLiveData<ViewState>()
    internal lateinit var job: Job

    fun loadSuperhero(heroId: Int) {
        job = GlobalScope.launch(uiContext) {
            liveData.value = ViewState.Loading()
            val result = withContext(backgroundContext) { repository.loadSuperHero(heroId) }
            when (result) {
                is Result.Success -> liveData.value = ViewState.Success(HeroDetailsViewData(result.data.name, result.data.description))
                is Result.Error -> liveData.value = ViewState.Error(result.exception)
            }

            liveData.value = ViewState.Finished()
        }
    }

    public override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
