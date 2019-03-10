package lt.setkus.superhero.data.model.heroes

import com.google.gson.annotations.SerializedName
import lt.setkus.superhero.data.model.Image

data class Character(
    @SerializedName("name") val name: String?,
    @SerializedName("thumbnail") val thumbnail: Image?
)