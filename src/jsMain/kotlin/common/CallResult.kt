package common

sealed class CallResult<out T> {
    class Error(val error: MacaoApiError) : CallResult<Nothing>()
    class Success<T>(val responseBody: T) : CallResult<T>()
}
