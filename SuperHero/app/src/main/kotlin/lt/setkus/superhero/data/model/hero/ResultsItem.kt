package lt.setkus.superhero.data.model.hero

import com.google.gson.annotations.SerializedName
import lt.setkus.superhero.data.model.Image

data class ResultsItem(
    @SerializedName("image")
    val image: Image?,
    @SerializedName("urls")
    val urls: List<UrlsItem>??,
    @SerializedName("stories")
    val stories: Stories?,
    @SerializedName("series")
    val series: Series?,
    @SerializedName("comics")
    val comics: Comics?,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("modified")
    val modified: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("resourceURI")
    val resourceURI: String? = "",
    @SerializedName("events")
    val events: Events?
)