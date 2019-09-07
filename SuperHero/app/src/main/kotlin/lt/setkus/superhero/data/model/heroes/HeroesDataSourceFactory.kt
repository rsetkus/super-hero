package lt.setkus.superhero.data.model.heroes

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import lt.setkus.superhero.app.heroes.SuperHeroViewData

class HeroesDataSourceFactory(val heroesDataSource: HeroesDataSource) : DataSource.Factory<Long, SuperHeroViewData>() {

    val mutableLiveData = MutableLiveData<HeroesDataSource>()

    /**
     * Create a DataSource.
     *
     *
     * The DataSource should invalidate itself if the snapshot is no longer valid. If a
     * DataSource becomes invalid, the only way to query more data is to create a new DataSource
     * from the Factory.
     *
     *
     * [LivePagedListBuilder] for example will construct a new PagedList and DataSource
     * when the current DataSource is invalidated, and pass the new PagedList through the
     * `LiveData<PagedList>` to observers.
     *
     * @return the new DataSource.
     */
    override fun create(): DataSource<Long, SuperHeroViewData> {
        mutableLiveData.postValue(heroesDataSource)
        return heroesDataSource
    }
}