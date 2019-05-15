package app.itgungnir.kwa.common.dto

data class NavigationResponse(
    val articles: List<ArticleResponse>,
    val cid: Int,
    val name: String
)