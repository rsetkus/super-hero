package lt.setkus.superhero.data.heroes

import com.google.gson.annotations.SerializedName

data class CharacterDataWrapper(@SerializedName("data") val data: CharacterDataContainer?)