package domain

import MacaoApiError
import data.CustomerProject
import data.CustomerProjectListRequest
import data.httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCustomerProjectsUseCase(
    private val dispatcher: Dispatchers,
) : SingleUseCase<CallResult<List<CustomerProject>>> {
    override suspend fun doWork(): CallResult<List<CustomerProject>> {
        return withContext(dispatcher.Default) {
            try {
                val response = httpClient.post(getCustomerProjects) {
                    contentType(ContentType.Application.Json)
                    setBody(CustomerProjectListRequest())
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
        private const val getCustomerProjects = "https://ktor-gae-401000.appspot.com/customer-project/list"
    }
}
