package lt.setkus.superhero.domain.heroes

interface SuperHeroesRepository {
    fun loadSuperHeroes(): List<SuperHero>
}