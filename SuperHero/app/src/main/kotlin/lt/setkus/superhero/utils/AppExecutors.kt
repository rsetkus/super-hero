package lt.setkus.superhero.utils

import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.newFixedThreadPoolContext

const val TREAD_COUNT = 3

open class AppExecutors constructor(
    val networkContext: CoroutineDispatcher = newFixedThreadPoolContext(TREAD_COUNT, "networkIO"),
    val uiContext: CoroutineDispatcher = Dispatchers.Main
)