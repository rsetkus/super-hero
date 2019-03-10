package lt.setkus.superhero.data.model.hero

import com.google.gson.annotations.SerializedName

data class UrlsItem(
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("url")
    val url: String? = ""
)