package lt.setkus.superhero.data.model.heroes

import com.google.gson.annotations.SerializedName

data class CharacterDataWrapper(@SerializedName("data") val data: CharacterDataContainer?)