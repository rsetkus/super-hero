package lt.setkus.superhero.util

import android.content.Context

fun getJson(context: Context, path: String): String {
    val inputStream = context.resources.assets.open(path)
    return inputStream.bufferedReader().use { it.readText() }
}