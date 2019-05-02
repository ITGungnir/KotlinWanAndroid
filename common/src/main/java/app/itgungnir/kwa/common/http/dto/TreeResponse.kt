package app.itgungnir.kwa.common.http.dto

data class TreeResponse(
    val children: List<TreeResponse>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)