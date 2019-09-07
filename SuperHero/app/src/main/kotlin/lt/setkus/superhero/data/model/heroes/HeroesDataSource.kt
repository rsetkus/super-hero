package lt.setkus.superhero.data.model.heroes

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lt.setkus.superhero.app.heroes.SuperHeroViewData
import lt.setkus.superhero.data.http.CHARACTERS_LIMIT
import lt.setkus.superhero.data.http.NetworkState
import lt.setkus.superhero.domain.Result
import lt.setkus.superhero.domain.heroes.SuperHero
import lt.setkus.superhero.domain.heroes.SuperHeroesRepository

class HeroesDataSource(
    val uiContext: CoroutineDispatcher,
    val backgroundContext: CoroutineDispatcher,
    val repository: SuperHeroesRepository,
    val viewMapper: (domain: SuperHero) -> SuperHeroViewData
) : PageKeyedDataSource<Long, SuperHeroViewData>() {

    val networkState = MutableLiveData<NetworkState>()

    /**
     * Load initial data.
     *
     *
     * This method is called first to initialize a PagedList with data. If it's possible to count
     * the items that can be loaded by the DataSource, it's recommended to pass the loaded data to
     * the callback via the three-parameter
     * [LoadInitialCallback.onResult]. This enables PagedLists
     * presenting data from this source to display placeholders to represent unloaded items.
     *
     *
     * [LoadInitialParams.requestedLoadSize] is a hint, not a requirement, so it may be may be
     * altered or ignored.
     *
     * @param params Parameters for initial load, including requested load size.
     * @param callback Callback that receives initial load data.
     */
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, SuperHeroViewData>) {
        networkState.postValue(NetworkState.LOADING)
        GlobalScope.launch(uiContext) {
            val result = withContext(backgroundContext) { repository.loadSuperHeroes(0) }
            when (result) {
                is Result.Success<List<SuperHero>> -> {
                    networkState.postValue(NetworkState.LOADED)
                    callback.onResult(result.data.map(viewMapper), 0, CHARACTERS_LIMIT)
                }
                is Result.Error -> networkState.postValue(NetworkState.ERROR)
            }
        }
    }

    /**
     * Append page with the key specified by [LoadParams.key].
     *
     *
     * It's valid to return a different list size than the page size if it's easier, e.g. if your
     * backend defines page sizes. It is generally safer to increase the number loaded than reduce.
     *
     *
     * Data may be passed synchronously during the load method, or deferred and called at a
     * later time. Further loads going down will be blocked until the callback is called.
     *
     *
     * If data cannot be loaded (for example, if the request is invalid, or the data would be stale
     * and inconsistent, it is valid to call [.invalidate] to invalidate the data source,
     * and prevent further loading.
     *
     * @param params Parameters for the load, including the key for the new page, and requested load
     * size.
     * @param callback Callback that receives loaded data.
     */
    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, SuperHeroViewData>) {
        GlobalScope.launch(uiContext) {
            networkState.postValue(NetworkState.LOADING)
            val nextOffset = params.key + CHARACTERS_LIMIT
            val result = withContext(backgroundContext) { repository.loadSuperHeroes(nextOffset) }
            when (result) {
                is Result.Success<List<SuperHero>> -> {
                    networkState.postValue(NetworkState.LOADED)
                    callback.onResult(result.data.map(viewMapper), nextOffset)
                }
                is Result.Error -> networkState.postValue(NetworkState.ERROR)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, SuperHeroViewData>) {
    }
}