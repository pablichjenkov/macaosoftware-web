package demo.domain

import common.MacaoApiError
import common.CallResult
import common.SingleNoInputUseCase
import demo.data.CustomerProject
import demo.domain.model.CustomerProjectListRequest
import common.httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCustomerProjectListUseCase(
    private val dispatcher: Dispatchers,
) : SingleNoInputUseCase<CallResult<List<CustomerProject>>> {

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
