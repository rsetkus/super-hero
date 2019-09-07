package lt.setkus.superhero.app.heroes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_heroes.heroesGrid
import kotlinx.android.synthetic.main.fragment_heroes.heroesStateLayout
import lt.setkus.superhero.R
import lt.setkus.superhero.data.http.NetworkState
import org.koin.android.ext.android.inject

private const val DEFAULT_NUMBER_OF_COLUMNS = 2

class HeroesFragment : Fragment() {

    val adapter = HeroesAdapter()

    private val viewModel: SuperHeroesViewModel by inject()
    private var numColumns = DEFAULT_NUMBER_OF_COLUMNS

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_heroes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindResources()

        heroesGrid.adapter = adapter
        val layoutManager = GridLayoutManager(context, numColumns)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = if (adapter.isSpannable(position)) layoutManager.spanCount else 1
        }

        heroesGrid.layoutManager = layoutManager
    }

    private fun bindResources() {
        numColumns = resources.getInteger(R.integer.num_columns)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkStateObserver = Observer<NetworkState> { networkState ->
            when (networkState) {
                is NetworkState.LOADED -> heroesStateLayout.content()
                is NetworkState.ERROR -> heroesStateLayout.error()
                is NetworkState.LOADING -> heroesStateLayout.loading()
            }
        }

        viewModel.netWorkState.observe(this, networkStateObserver)

        viewModel.heroesLiveData.observe(this, Observer<PagedList<SuperHeroViewData>> {
            adapter.submitList(it)
        })
    }
}