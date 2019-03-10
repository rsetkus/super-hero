package lt.setkus.superhero.data.heroe

import com.google.gson.annotations.SerializedName

data class UrlsItem(
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("url")
    val url: String? = ""
)