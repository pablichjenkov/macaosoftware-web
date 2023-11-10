package demo.domain.usecase

import http.MacaoApiError
import util.Result
import util.SingleInputUseCase
import http.httpClient
import demo.data.CustomerProject
import demo.domain.model.CustomerProjectUpdateRequest
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateCustomerProjectByOwnerIdUseCase(
    private val dispatcher: Dispatchers
) : SingleInputUseCase<CustomerProjectUpdateRequest, Result<CustomerProject>> {

    override suspend fun doWork(
        updateRequest: CustomerProjectUpdateRequest
    ): Result<CustomerProject> {
        return withContext(dispatcher.Default) {
            try {
                val response = httpClient.post(updateCustomerProjectByOwnerId) {
                    contentType(ContentType.Application.Json)
                    setBody(updateRequest)
                }
                if (response.status.isSuccess()) {
                    Result.Success(response.body())
                } else {
                    Result.Error(response.body())
                }
            } catch (th: Throwable) {
                th.printStackTrace()
                Result.Error(MacaoApiError.fromException(th))
            }
        }
    }

    companion object {
        private const val updateCustomerProjectByOwnerId =
            "https://ktor-gae-401000.appspot.com/customer-project/update"
    }
}
