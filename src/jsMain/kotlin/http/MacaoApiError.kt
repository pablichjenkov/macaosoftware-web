package http

import kotlinx.serialization.Serializable
import util.MacaoError

@Serializable
data class MacaoApiError(
    val instanceId: Long,
    val errorCode: Int,
    val errorDescription: String
) : MacaoError {
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
