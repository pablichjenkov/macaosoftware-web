package demo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CustomerProjectListRequest(
    val offset: Int = 0,
    val limit: Int = 5
)
