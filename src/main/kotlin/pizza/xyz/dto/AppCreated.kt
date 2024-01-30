package pizza.xyz.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppCreated(
    @SerialName("id") val id: String = "",
)