package lt.setkus.superhero.app.heroes

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import lt.setkus.superhero.data.http.CHARACTERS_LIMIT
import lt.setkus.superhero.data.http.NetworkState
import lt.setkus.superhero.data.model.heroes.HeroesDataSourceFactory

class SuperHeroesViewModel(
    val factory: HeroesDataSourceFactory
) : ViewModel() {

    private val pagedListConfig: PagedList.Config
    val netWorkState: LiveData<NetworkState>
    val heroesLiveData: LiveData<PagedList<SuperHeroViewData>>

    init {
        pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(CHARACTERS_LIMIT.toInt())
            .build()

        netWorkState = Transformations.switchMap(factory.mutableLiveData, { it.networkState })

        heroesLiveData = LivePagedListBuilder(factory, pagedListConfig).build()
    }
}