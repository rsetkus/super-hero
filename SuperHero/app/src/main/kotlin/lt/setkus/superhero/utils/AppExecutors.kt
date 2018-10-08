package lt.setkus.superhero.utils

import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import kotlin.coroutines.experimental.CoroutineContext

const val TREAD_COUNT = 3

open class AppExecutors constructor(
    val networkContext: CoroutineContext = newFixedThreadPoolContext(TREAD_COUNT, "networkIO"),
    val uiContext: CoroutineContext = Dispatchers.Main
)