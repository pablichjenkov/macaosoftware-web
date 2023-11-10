package demo.domain.usecase

import http.MacaoApiError
import util.Result
import util.SingleNoInputUseCase
import http.httpClient
import demo.data.CustomerProject
import demo.domain.model.CustomerProjectListRequest
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCustomerProjectListUseCase(
    private val dispatcher: Dispatchers,
) : SingleNoInputUseCase<Result<List<CustomerProject>>> {

    override suspend fun doWork(): Result<List<CustomerProject>> {
        return withContext(dispatcher.Default) {
            try {
                val response = httpClient.post(getCustomerProjects) {
                    contentType(ContentType.Application.Json)
                    setBody(CustomerProjectListRequest())
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
        private const val getCustomerProjects = "https://ktor-gae-401000.appspot.com/customer-project/list"
    }
}
