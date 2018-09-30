package lt.setkus.superhero.app.heroes

import lt.setkus.superhero.domain.heroes.SuperHeroesRepository

class SuperHeroesViewModel(val repository: SuperHeroesRepository) {

    fun loadSuperHeroes() {
        repository.loadSuperHeroes()
    }
}