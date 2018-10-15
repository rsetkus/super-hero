package lt.setkus.superhero.app.heroes

import lt.setkus.superhero.data.heroes.SuperHeroesDataRepository
import org.koin.dsl.module.module

val superHeroesModule = module {
    single { SuperHeroesDataRepository(get()) }
}