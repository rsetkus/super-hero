package lt.setkus.superhero.app.heroes

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import lt.setkus.superhero.app.common.ViewState
import org.koin.android.ext.android.inject
import superhero.setkus.lt.superhero.R

class MainActivity : FragmentActivity() {

    private val viewModel: SuperHeroesViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val observer = Observer<ViewState> { viewState ->
            println(viewState)
        }

        viewModel.superHeroesLiveData.observe(this, observer)

        viewModel.loadSuperHeroes()
    }
}
