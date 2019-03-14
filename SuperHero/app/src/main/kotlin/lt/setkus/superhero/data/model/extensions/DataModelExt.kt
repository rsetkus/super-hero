package lt.setkus.superhero.data.model.extensions

import lt.setkus.superhero.data.model.Image

fun Image.getImageUrlString() = path?.plus(".").plus(this?.extension)