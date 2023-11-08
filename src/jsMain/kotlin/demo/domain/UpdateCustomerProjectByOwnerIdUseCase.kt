package demo.domain

import common.MacaoApiError
import common.CallResult
import common.SingleInputUseCase
import demo.data.CustomerProject
import common.httpClient
import demo.domain.model.CustomerProjectUpdateRequest
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateCustomerProjectByOwnerIdUseCase(
    private val dispatcher: Dispatchers
) : SingleInputUseCase<CustomerProjectUpdateRequest, CallResult<CustomerProject>> {

    override suspend fun doWork(
        updateRequest: CustomerProjectUpdateRequest
    ): CallResult<CustomerProject> {
        return withContext(dispatcher.Default) {
            try {
                val response = httpClient.post(updateCustomerProjectByOwnerId) {
                    contentType(ContentType.Application.Json)
                    setBody(updateRequest)
                }
                if (response.status.isSuccess()) {
                    CallResult.Success(response.body())
                } else {
                    CallResult.Error(MacaoApiError.fromErrorJsonString(response.bodyAsText()))
                }
            } catch (th: Throwable) {
                th.printStackTrace()
                CallResult.Error(MacaoApiError.fromException(th))
            }
        }
    }

    companion object {
        private const val updateCustomerProjectByOwnerId =
            "https://ktor-gae-401000.appspot.com/customer-project/update"
    }
}
