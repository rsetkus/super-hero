package lt.setkus.superhero.app.heroes

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val superHeroesModule = module {
    viewModel { SuperHeroesViewModel(get(name = "uiContext"), get(name = "networkContext"), get()) }
}