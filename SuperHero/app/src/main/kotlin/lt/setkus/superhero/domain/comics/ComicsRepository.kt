package lt.setkus.superhero.domain.comics

interface ComicsRepository {
    fun loadComicsByHero(heroid: Int): List<Comic>
}