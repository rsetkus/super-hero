package lt.setkus.superhero.app.details

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val heroDetailsModule = module {
    viewModel { HeroDetailsViewModel(get(name = "uiContext"), get(name = "networkContext"), get()) }
}