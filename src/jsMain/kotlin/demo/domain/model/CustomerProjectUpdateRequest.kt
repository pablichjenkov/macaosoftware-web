package demo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CustomerProjectUpdateRequest(
    val ownerId: String,
    val jsonMetadata: String
)