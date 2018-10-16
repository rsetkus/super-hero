package lt.setkus.superhero.app.heroes

import lt.setkus.superhero.data.heroes.SuperHeroesDataRepository
import lt.setkus.superhero.domain.heroes.SuperHeroesRepository
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val superHeroesModule = module {
    viewModel { SuperHeroesViewModel(get(name = "uiContext"), get(name = "networkContext"), get()) }
    single { SuperHeroesDataRepository(get()) as SuperHeroesRepository }
}