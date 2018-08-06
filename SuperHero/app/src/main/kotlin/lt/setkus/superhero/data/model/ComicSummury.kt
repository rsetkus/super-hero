package lt.setkus.superhero.data.model

import com.google.gson.annotations.SerializedName

data class ComicSummury(@SerializedName("name")
                        val name: String? = "",
                        @SerializedName("resourceURI")
                        val resourceURI: String? = "")