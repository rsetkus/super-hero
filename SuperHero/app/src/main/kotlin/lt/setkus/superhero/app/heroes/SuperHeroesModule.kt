package lt.setkus.superhero.app.heroes

import lt.setkus.superhero.data.heroes.SuperHeroesDataRepository
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val superHeroesModule = module {
    viewModel { SuperHeroesViewModel(get()) }
    single { SuperHeroesDataRepository(get(name = "uiContext"), get(name = "networkContext"), get()) }
}