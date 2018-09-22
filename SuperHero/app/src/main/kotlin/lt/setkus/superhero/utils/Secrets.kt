package lt.setkus.superhero.utils

import android.util.Base64
import superhero.setkus.lt.superhero.BuildConfig

fun getPrivateKey(): String = decodeBase64(decrypt(BuildConfig.PRIVATE_KEY, BuildConfig.KEY))
fun getPublicKey(): String = decodeBase64(decrypt(BuildConfig.PUBLIC_KEY, BuildConfig.KEY))

private fun decrypt(input: IntArray, key: String): String {
    val output = StringBuilder()
    input.forEachIndexed { index, i ->
        output.append((i - 48).xor(key.toCharArray().get(index % (key.length - 1)).toInt()).toChar())
    }
    return output.toString()
}

private fun decodeBase64(input: String): String = String(Base64.decode(input, Base64.DEFAULT))