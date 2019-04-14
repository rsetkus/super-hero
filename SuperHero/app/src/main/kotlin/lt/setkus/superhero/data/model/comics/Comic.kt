package lt.setkus.superhero.data.model.comics

import com.google.gson.annotations.SerializedName
import lt.setkus.superhero.data.model.Image

class Comic(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("thumbnail") val thumbnail: Image?
)