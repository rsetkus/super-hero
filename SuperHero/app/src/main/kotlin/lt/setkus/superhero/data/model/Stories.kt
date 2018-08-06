package lt.setkus.superhero.data.model

import com.google.gson.annotations.SerializedName

data class Stories(@SerializedName("collectionURI")
                   val collectionURI: String? = "",
                   @SerializedName("available")
                   val available: String? = "",
                   @SerializedName("returned")
                   val returned: String? = "",
                   @SerializedName("items")
                   val items: List<ItemsItem>??)