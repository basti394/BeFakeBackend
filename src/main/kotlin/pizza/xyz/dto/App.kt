package pizza.xyz.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class App(
    @SerialName("version") val version: String,
    @SerialName("name") val name: String,
    @SerialName("platform") val platform: String,
)