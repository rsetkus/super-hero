package lt.setkus.superhero.app.heroes

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import lt.setkus.superhero.app.common.ViewState
import superhero.setkus.lt.superhero.R

class MainActivity : FragmentActivity() {

    private lateinit var viewModel: SuperHeroesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(SuperHeroesViewModel::class.java)
        val observer = Observer<ViewState> { viewState ->
            println(viewState)
        }

        viewModel.superHeroesLiveData.observe(this, observer)
    }
}
