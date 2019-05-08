package app.itgungnir.kwa.mine

import app.itgungnir.kwa.common.widget.easy_adapter.ListItem
import my.itgungnir.rxmvvm.core.mvvm.State

data class MineState(
    val refreshing: Boolean = false,
    val items: List<ListItem> = listOf(MineProfileVO()),
    val loading: Boolean = false,
    val hasMore: Boolean = false,
    val error: Throwable? = null
) : State {

    data class MineProfileVO(
        val userName: String? = null,
        val isListEmpty: Boolean = false
    ) : ListItem

    data class MineArticleVO(
        val author: String,
        val category: String,
        val categoryId: Int,
        val title: String,
        val date: String,
        val link: String
    ) : ListItem
}