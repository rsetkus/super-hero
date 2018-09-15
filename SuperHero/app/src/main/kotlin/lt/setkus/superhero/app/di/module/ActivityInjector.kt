package lt.setkus.superhero.app.di.module

import dagger.Component
import lt.setkus.superhero.app.MainActivity
import javax.inject.Singleton

/**
 * Component providing inject() functions for activities
 */
@Singleton
@Component(modules = [(HttpModule::class)])
interface ActivityInjector {

    /**
     * Injects required dependencies into MainActivity
     *
     * @param mainActivity MainActivity
     */
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {
        fun build(): ActivityInjector

        fun httpModule(httpModule: HttpModule): Builder
    }
}