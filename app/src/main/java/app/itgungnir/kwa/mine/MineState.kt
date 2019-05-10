package app.itgungnir.kwa.mine

import app.itgungnir.kwa.common.widget.easy_adapter.ListItem
import my.itgungnir.rxmvvm.core.mvvm.State

data class MineState(
    val refreshing: Boolean = false,
    val items: List<MineArticleVO> = listOf(),
    val loading: Boolean = false,
    val hasMore: Boolean? = null,
    val error: Throwable? = null
) : State {

    data class MineArticleVO(
        val id: Int,
        val originId: Int,
        val author: String,
        val category: String,
        val categoryId: Int,
        val title: String,
        val date: String,
        val link: String
    ) : ListItem
}