package app.itgungnir.kwa.project.child

import app.itgungnir.kwa.common.widget.easy_adapter.ListItem
import my.itgungnir.rxmvvm.core.mvvm.State

data class ProjectChildState(
    val refreshing: Boolean = false,
    val items: List<ProjectArticleVO> = listOf(),
    val loading: Boolean = false,
    val hasMore: Boolean = false,
    val error: Throwable? = null
) : State {

    data class ProjectArticleVO(
        val id: Int,
        val originId: Int,
        val cover: String,
        val title: String,
        val author: String,
        val desc: String,
        val date: String,
        val link: String,
        val repositoryLink: String
    ) : ListItem
}