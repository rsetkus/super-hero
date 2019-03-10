package lt.setkus.superhero.data.model.hero

import com.google.gson.annotations.SerializedName

data class ItemsItem(
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("resourceURI")
    val resourceURI: String? = ""
)