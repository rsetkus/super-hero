package lt.setkus.superhero.app.heroes

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import lt.setkus.superhero.domain.heroes.SuperHero
import lt.setkus.superhero.domain.heroes.SuperHeroesRepository

class SuperHeroesViewModel(val repository: SuperHeroesRepository) : ViewModel() {

    val superHeroesLiveData = MutableLiveData<List<SuperHero>>()

    fun loadSuperHeroes() {
        val superHeroes = repository.loadSuperHeroes()
        superHeroesLiveData.value = superHeroes
    }
}