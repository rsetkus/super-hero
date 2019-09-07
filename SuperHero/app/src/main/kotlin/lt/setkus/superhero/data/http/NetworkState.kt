package lt.setkus.superhero.data.http

sealed class NetworkState {
    object LOADING : NetworkState()
    object LOADED : NetworkState()
    object ERROR : NetworkState()
}