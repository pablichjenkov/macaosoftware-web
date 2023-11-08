package common

import io.ktor.utils.io.errors.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class MacaoApiError(
    val error: String = "No field error",
    @SerialName("error_description")
    val errorDescription: String = "No field errorDescription",
    val code: Int = 0,
    val title: String = "Unknown Error"
) {
    companion object {

        private fun fromAmadeusErrorInList(issue: Issue): MacaoApiError {
            return MacaoApiError(
                title = issue.title,
                errorDescription = issue.detail,
                code = issue.code,
                error = issue.status.toString()
            )
        }

        fun fromException(throwable: Throwable): MacaoApiError {
            return MacaoApiError(
                title = when (throwable) {
                    is IOException -> "IOException"
                    else -> "Unknown Exception"
                },
                errorDescription = throwable.message.orEmpty()
            )
        }

        fun fromErrorJsonString(errorJsonString: String): MacaoApiError {
            val amadeusSingleError = try {
                Json.decodeFromString<MacaoApiError>(errorJsonString)
            } catch (ex: Exception) {
                ex.printStackTrace()
                null
            }
            if (amadeusSingleError != null) {
                return amadeusSingleError
            }

            val amadeusErrorList = try {
                Json.decodeFromString<AmadeusErrorList>(errorJsonString)
            } catch (ex: Exception) {
                ex.printStackTrace()
                null
            }

            val firstErrorInList = amadeusErrorList?.errors?.firstOrNull() ?: Issue()

            return fromAmadeusErrorInList(firstErrorInList)
        }
    }
}

@Serializable
private class AmadeusErrorList(
    val errors: List<Issue>
)

@Serializable
private class Issue(
    val status: Int = 0,
    val code: Int = 0,
    val title: String = "Unknown Error",
    val detail: String = "Unknown Detail"
)
