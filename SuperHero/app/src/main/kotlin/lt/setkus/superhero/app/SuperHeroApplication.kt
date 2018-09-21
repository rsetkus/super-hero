package lt.setkus.superhero.app

import android.app.Application
import lt.setkus.superhero.app.di.superHeroModules
import org.koin.android.ext.android.startKoin
import superhero.setkus.lt.superhero.BuildConfig
import timber.log.Timber

class SuperHeroApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin(this, superHeroModules)
    }
}