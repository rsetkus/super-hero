package lt.setkus.superhero.data.model.heroes

import com.google.gson.annotations.SerializedName
import lt.setkus.superhero.data.model.Image

data class Character(
    @SerializedName("id") val heroId: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("thumbnail") val thumbnail: Image?
)