package app.itgungnir.kwa.hierarchy

import app.itgungnir.kwa.common.widget.easy_adapter.ListItem
import my.itgungnir.rxmvvm.core.mvvm.State

data class HierarchyChildState(
    val refreshing: Boolean = false,
    val items: List<ArticleVO> = listOf(),
    val loading: Boolean = false,
    val hasMore: Boolean = false,
    val error: Throwable? = null
) : State {

    data class ArticleVO(
        val author: String,
        val title: String,
        val date: String,
        val link: String
    ) : ListItem
}