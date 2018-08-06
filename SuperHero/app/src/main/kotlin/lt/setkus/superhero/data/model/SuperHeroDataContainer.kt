package lt.setkus.superhero.data.model

import com.google.gson.annotations.SerializedName

data class SuperHeroDataContainer(@SerializedName("total")
                                  val total: String? = "",
                                  @SerializedName("offset")
                                  val offset: String? = "",
                                  @SerializedName("limit")
                                  val limit: String? = "",
                                  @SerializedName("count")
                                  val count: String? = "",
                                  @SerializedName("results")
                                  val results: List<ResultsItem>??)