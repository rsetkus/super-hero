package lt.setkus.superhero.data.http

import retrofit2.Retrofit

class MarvelService {

    companion object {
        fun createCharacterService(retrofit: Retrofit) = retrofit.create(CharacterService::class.java)
    }
}