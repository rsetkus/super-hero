package lt.setkus.superhero.data.model.comics

import com.google.gson.annotations.SerializedName

class ComicDataContainer(@SerializedName("results") val comics: List<Comic>?)