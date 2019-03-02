package lt.setkus.superhero.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newFixedThreadPoolContext

const val TREAD_COUNT = 3

open class AppExecutors constructor(
    val networkContext: CoroutineDispatcher = newFixedThreadPoolContext(TREAD_COUNT, "networkIO"),
    val uiContext: CoroutineDispatcher = Dispatchers.Main
)