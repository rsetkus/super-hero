package lt.setkus.superhero.data.hero

import com.google.gson.annotations.SerializedName

data class Events(
    @SerializedName("collectionURI")
    val collectionURI: String? = "",
    @SerializedName("available")
    val available: Int? = 0,
    @SerializedName("returned")
    val returned: Int? = 0,
    @SerializedName("items")
    val items: List<ItemsItem>?
)