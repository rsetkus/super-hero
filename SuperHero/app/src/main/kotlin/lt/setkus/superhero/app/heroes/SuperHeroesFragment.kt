package lt.setkus.superhero.app.heroes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import lt.setkus.superhero.R
import lt.setkus.superhero.app.common.ViewState
import org.koin.android.ext.android.inject
import timber.log.Timber

class SuperHeroesFragment : Fragment() {

    private val viewModel: SuperHeroesViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.d("View has been created.")
        return inflater.inflate(R.layout.super_heroes_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("onCreate has been called")
        Timber.d(viewModel.toString())
        val observer = Observer<ViewState> { viewState ->
            println(viewState)
        }

        viewModel.superHeroesLiveData.observe(this, observer)

        viewModel.loadSuperHeroes()
    }

    companion object {
        fun newInstance() = SuperHeroesFragment()
    }
}