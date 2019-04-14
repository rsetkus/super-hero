package lt.setkus.superhero.app.di

import lt.setkus.superhero.data.model.comics.ComicsDataRepository
import lt.setkus.superhero.data.model.heroes.SuperHeroesDataRepository
import lt.setkus.superhero.domain.comics.ComicsRepository
import lt.setkus.superhero.domain.heroes.SuperHeroesRepository
import org.koin.dsl.module.module

val applicationModule = module {
    single { SuperHeroesDataRepository(get()) as SuperHeroesRepository }
    single { ComicsDataRepository(get()) as ComicsRepository }
}