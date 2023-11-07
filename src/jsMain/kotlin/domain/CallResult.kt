package domain

import MacaoApiError

sealed class CallResult<out T> {
    class Error(val error: MacaoApiError) : CallResult<Nothing>()
    class Success<T>(val responseBody: T) : CallResult<T>()
}
