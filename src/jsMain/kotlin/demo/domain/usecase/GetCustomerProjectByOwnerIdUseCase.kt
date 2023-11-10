package demo.domain.usecase

import util.Result
import http.MacaoApiError
import util.SingleInputUseCase
import http.httpClient
import demo.data.CustomerProject
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCustomerProjectByOwnerIdUseCase(
    private val dispatcher: Dispatchers
) : SingleInputUseCase<String, Result<CustomerProject>> {

    override suspend fun doWork(ownerId: String): Result<CustomerProject> {
        return withContext(dispatcher.Default) {
            try {
                val response = httpClient.get(getCustomerProjectByOwnerId) {
                    url {
                        appendEncodedPathSegments(ownerId)
                    }
                }
                if (response.status.isSuccess()) {
                    Result.Success(response.body<CustomerProject>())
                } else {
                    Result.Error(response.body<MacaoApiError>())
                }
            } catch (th: Throwable) {
                th.printStackTrace()
                Result.Error(MacaoApiError.fromException(th))
            }
        }
    }

    companion object {
        private const val getCustomerProjectByOwnerId =
            "https://ktor-gae-401000.appspot.com/customer-project/"
    }
}
