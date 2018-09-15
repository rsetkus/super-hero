package lt.setkus.superhero.app.di.module

import dagger.Module
import dagger.Provides
import lt.setkus.superhero.data.http.CharacterService
import lt.setkus.superhero.data.http.MarvelService

@Module
object HttpModule {

    @Provides
    @JvmStatic
    internal fun provideCharacterService(): CharacterService = MarvelService.getCharacterService()
}