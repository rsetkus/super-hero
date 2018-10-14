package lt.setkus.superhero.app.heroes

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import lt.setkus.superhero.app.common.ViewState
import lt.setkus.superhero.domain.Result
import lt.setkus.superhero.domain.heroes.SuperHeroesRepository

class SuperHeroesViewModel(
    val uiContext: CoroutineDispatcher,
    val backgroundContext: CoroutineDispatcher,
    val repository: SuperHeroesRepository
) : ViewModel() {

    val superHeroesLiveData = MutableLiveData<ViewState>()

    fun loadSuperHeroes() = GlobalScope.launch(uiContext) {
        superHeroesLiveData.value = ViewState.Loading()
        val superHeroes = withContext(backgroundContext) { repository.loadSuperHeroes() }
        when (superHeroes) {
            is Result.Success -> superHeroesLiveData.value = ViewState.Success(superHeroes.data)
            is Result.Error -> superHeroesLiveData.postValue(ViewState.Error(superHeroes.exception))
        }
        superHeroesLiveData.value = ViewState.Finished()
    }
}