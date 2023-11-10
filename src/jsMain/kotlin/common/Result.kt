package common

sealed class Result<out T> {
    class Success<T>(val value: T) : Result<T>()
    class Error(val error: MacaoApiError) : Result<Nothing>()
}

sealed class Result2<out S, out E> {
    class Success<T>(val value: T) : Result2<T, Nothing>()
    class Error<V>(val error: V) : Result2<Nothing, V>()
}
