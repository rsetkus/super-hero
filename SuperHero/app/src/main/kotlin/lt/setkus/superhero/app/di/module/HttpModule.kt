package lt.setkus.superhero.app.di.module

import lt.setkus.superhero.data.http.MarvelService
import org.koin.dsl.module.module

val HttpModule = module {
    factory { MarvelService.getCharacterService() }
}