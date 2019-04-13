package lt.setkus.superhero.domain.heroes

data class SuperHero(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String?
)