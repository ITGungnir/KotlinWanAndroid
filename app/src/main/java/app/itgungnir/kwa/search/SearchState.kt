package app.itgungnir.kwa.search

import app.itgungnir.kwa.common.widget.easy_adapter.ListItem
import my.itgungnir.rxmvvm.core.mvvm.State

data class SearchState(
    val items: List<ListItem> = listOf(),
    val error: Throwable? = null
) : State {

    data class SearchHotKeyVO(
        val data: List<SearchTagVO>
    ) : ListItem

    data class SearchHistoryVO(
        val data: List<SearchTagVO>
    ) : ListItem

    data class SearchTagVO(
        val name: String
    ) : ListItem
}