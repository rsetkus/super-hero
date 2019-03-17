package lt.setkus.superhero.app

import android.app.Application
import lt.setkus.superhero.BuildConfig
import lt.setkus.superhero.app.details.heroDetailsModule
import lt.setkus.superhero.app.di.applicationModule
import lt.setkus.superhero.app.di.module.httpModule
import lt.setkus.superhero.app.heroes.superHeroesModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class SuperHeroApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin(this,
            listOf(
                applicationModule,
                httpModule,
                superHeroesModule,
                heroDetailsModule
            )
        )
    }
}