package lt.setkus.superhero.app.heroes

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

class SuperHeroesViewModel(
    val uiContext: CoroutineDispatcher,
    val backgroundContext: CoroutineDispatcher,
    val repository: SuperHeroesRepository
) : ViewModel() {

    val superHeroesLiveData = MutableLiveData<ViewState>()
    internal lateinit var job: Job

    fun loadSuperHeroes() {
        job = GlobalScope.launch(uiContext) {
            superHeroesLiveData.value = ViewState.Loading()
            val superHeroes = withContext(backgroundContext) { repository.loadSuperHeroes() }
            when (superHeroes) {
                is Result.Success -> {
                    val viewData = superHeroes.data.map { SuperHeroViewData(it.name, it.id, it.imageUrl) }
                    superHeroesLiveData.value = ViewState.Success(viewData)
                }
                is Result.Error -> superHeroesLiveData.value = ViewState.Error(superHeroes.exception)
            }
            superHeroesLiveData.value = ViewState.Finished()
        }
    }

    public override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}