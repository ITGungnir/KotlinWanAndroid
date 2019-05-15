package app.itgungnir.kwa.common.dto

data class LoginResponse(
    val chapterTops: List<Any>,
    val collectIds: Set<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val password: String,
    val token: String,
    val type: Int,
    val username: String
)