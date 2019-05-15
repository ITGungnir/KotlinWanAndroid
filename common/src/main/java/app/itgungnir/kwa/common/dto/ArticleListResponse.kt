package app.itgungnir.kwa.common.dto

data class ArticleListResponse(
    val curPage: Int,
    val datas: List<ArticleResponse>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)