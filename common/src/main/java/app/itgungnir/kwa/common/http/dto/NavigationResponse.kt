package app.itgungnir.kwa.common.http.dto

data class NavigationResponse(
    val articles: List<ArticleResponse>,
    val cid: Int,
    val name: String
)