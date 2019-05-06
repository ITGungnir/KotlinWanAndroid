package app.itgungnir.kwa.common.http.dto

data class TabResponse(
    val children: List<TabResponse>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)