package lt.setkus.superhero.data.model.hero

import com.google.gson.annotations.SerializedName

data class Thumbnail(
    @SerializedName("path")
    val path: String? = "",
    @SerializedName("extension")
    val extension: String? = ""
)