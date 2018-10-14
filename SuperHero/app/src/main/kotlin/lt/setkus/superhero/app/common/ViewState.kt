package lt.setkus.superhero.app.common

sealed class ViewState {
    class Loading : ViewState()
    data class Success<out T : Any>(val data: T) : ViewState()
    class Error(val exception: Throwable) : ViewState()
    class Finished : ViewState()
}