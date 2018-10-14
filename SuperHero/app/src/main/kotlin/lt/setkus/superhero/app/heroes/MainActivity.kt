package lt.setkus.superhero.app.heroes

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import lt.setkus.superhero.app.common.ViewState
import superhero.setkus.lt.superhero.R

class MainActivity : AppCompatActivity() {

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
