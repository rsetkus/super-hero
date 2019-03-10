package lt.setkus.superhero.data.heroe

import com.google.gson.annotations.SerializedName

data class Thumbnail(
    @SerializedName("path")
    val path: String? = "",
    @SerializedName("extension")
    val extension: String? = ""
)