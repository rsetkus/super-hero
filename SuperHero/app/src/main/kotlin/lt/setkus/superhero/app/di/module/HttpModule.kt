package lt.setkus.superhero.app.di.module

import lt.setkus.superhero.data.http.MarvelService
import lt.setkus.superhero.data.http.OkHttpClientProvider
import lt.setkus.superhero.data.http.provideRetrofit
import lt.setkus.superhero.utils.AppExecutors
import org.koin.dsl.module.module

val httpModule = module {
    single { OkHttpClientProvider.getClient() }
    single { provideRetrofit(get()) }
    single { MarvelService.createCharacterService(get()) }
    single(name = "uiContext") { AppExecutors().uiContext }
    single(name = "networkContext") { AppExecutors().networkContext }
}