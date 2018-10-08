package lt.setkus.superhero.app.di.module

import lt.setkus.superhero.data.http.MarvelService
import lt.setkus.superhero.utils.AppExecutors
import org.koin.dsl.module.module

val httpModule = module {
    factory { MarvelService.getCharacterService() }
    single(name = "uiContext") { AppExecutors().uiContext }
    single(name = "networkContext") { AppExecutors().networkContext }
}