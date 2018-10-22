package lt.setkus.superhero.data.http

import retrofit2.Retrofit

class MarvelService(val retrofit: Retrofit) {
    fun getCharacterService(): CharacterService = retrofit.create(CharacterService::class.java)
}