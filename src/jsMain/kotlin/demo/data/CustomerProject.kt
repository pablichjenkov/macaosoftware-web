package demo.data

import kotlinx.serialization.Serializable

@Serializable
class CustomerProject(
    val ownerId: String,
    val jsonMetadata: String
)
