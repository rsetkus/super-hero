package lt.setkus.superhero.app.heroes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lt.setkus.superhero.R
import lt.setkus.superhero.app.common.ViewState
import org.koin.android.ext.android.inject

class SuperHeroesFragment : Fragment() {

    private lateinit var grid: RecyclerView

    private lateinit var layoutManager: GridLayoutManager
    private val adapter = SuperHeroesAdapter()

    private val viewModel: SuperHeroesViewModel by inject()

    private var numColumns = 2 // default number of columns in the grid

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.super_heroes_fragment, container, false)

        bindResources()

        grid = rootView.findViewById(R.id.grid)
        layoutManager = GridLayoutManager(context, numColumns)
        grid.layoutManager = layoutManager
        grid.adapter = adapter

        return rootView
    }

    private fun bindResources() {
        numColumns = resources.getInteger(R.integer.num_columns)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val observer = Observer<ViewState> { viewState ->
            when (viewState) {
                is ViewState.Success<*> -> {
                    adapter.addSuperHeroes(viewState.data as List<SuperHeroViewData>)
                }
            }
        }

        viewModel.superHeroesLiveData.observe(this, observer)

        viewModel.loadSuperHeroes()
    }

    companion object {
        fun newInstance() = SuperHeroesFragment()
    }
}