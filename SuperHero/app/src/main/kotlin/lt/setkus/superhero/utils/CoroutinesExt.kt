package lt.setkus.superhero.utils

import kotlinx.coroutines.experimental.Deferred
import lt.setkus.superhero.domain.Result

suspend fun <T : Any> Deferred<T>.awaitResult(): Result<T> = try {
    Result.Success(await())
} catch (e: Throwable) {
    Result.Error(e)
}