package lt.setkus.superhero.data.heroes

import com.google.gson.annotations.SerializedName

data class CharacterDataContainer(@SerializedName("results") val results: List<Character>?)