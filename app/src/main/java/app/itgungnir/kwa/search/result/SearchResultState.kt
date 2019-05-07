package app.itgungnir.kwa.search.result

import app.itgungnir.kwa.common.widget.easy_adapter.ListItem
import my.itgungnir.rxmvvm.core.mvvm.State

data class SearchResultState(
    val refreshing: Boolean = false,
    val items: List<SearchResultArticleVO> = listOf(),
    val loading: Boolean = false,
    val hasMore: Boolean = false,
    val error: Throwable? = null
) : State {

    data class SearchResultArticleVO(
        val author: String,
        val title: String,
        val date: String,
        val link: String
    ) : ListItem
}