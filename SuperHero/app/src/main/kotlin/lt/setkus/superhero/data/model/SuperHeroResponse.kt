package lt.setkus.superhero.data.model

import com.google.gson.annotations.SerializedName

data class SuperHeroResponse(@SerializedName("copyright")
                             val copyright: String? = "",
                             @SerializedName("code")
                             val code: String? = "",
                             @SerializedName("data")
                             val data: SuperHeroDataContainer?,
                             @SerializedName("attributionHTML")
                             val attributionHTML: String? = "",
                             @SerializedName("attributionText")
                             val attributionText: String? = "",
                             @SerializedName("etag")
                             val etag: String? = "",
                             @SerializedName("status")
                             val status: String? = "")