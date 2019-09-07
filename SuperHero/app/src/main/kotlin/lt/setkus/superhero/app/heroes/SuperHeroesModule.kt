package lt.setkus.superhero.app.heroes

import lt.setkus.superhero.data.model.heroes.HeroesDataSource
import lt.setkus.superhero.data.model.heroes.HeroesDataSourceFactory
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val superHeroesModule = module {
    single { superHeroViewDataMapper }
    single { HeroesDataSource(get(name = "uiContext"), get(name = "networkContext"), get(), get()) }
    single { HeroesDataSourceFactory(get()) }
    viewModel { SuperHeroesViewModel(get()) }
}