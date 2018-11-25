package lt.setkus.superhero.app.heroes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_heroes.*
import lt.setkus.superhero.R
import lt.setkus.superhero.app.common.ViewState
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

        val layoutManager = GridLayoutManager(context, numColumns)
        custom_fonts_grid.layoutManager = layoutManager
        custom_fonts_grid.adapter = adapter
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
        fun newHeroesFragment() = HeroesFragment()
    }
}