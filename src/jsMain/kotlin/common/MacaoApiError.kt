package common

import kotlinx.serialization.Serializable

@Serializable
data class MacaoApiError(
    val instanceId: Long,
    val errorCode: Int,
    val errorDescription: String
) {
    companion object {
        fun fromException(th: Throwable): MacaoApiError {
            return MacaoApiError(
                instanceId = 0,
                errorCode = 0,
                errorDescription = "Exception message: ${th.message}"
            )
        }
    }
}
