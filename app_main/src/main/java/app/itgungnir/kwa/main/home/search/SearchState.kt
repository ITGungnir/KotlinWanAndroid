package app.itgungnir.kwa.main.home.search

import my.itgungnir.rxmvvm.core.mvvm.State
import my.itgungnir.ui.easy_adapter.ListItem

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