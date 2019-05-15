package app.itgungnir.kwa.common.dto

data class TagResponse(
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)