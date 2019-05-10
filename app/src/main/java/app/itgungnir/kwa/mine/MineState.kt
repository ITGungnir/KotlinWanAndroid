package app.itgungnir.kwa.mine

import app.itgungnir.kwa.common.widget.easy_adapter.ListItem
import my.itgungnir.rxmvvm.core.mvvm.State

data class MineState(
    val refreshing: Boolean = false,
    val items: List<MineDataVO> = listOf(),
    val loading: Boolean = false,
    val hasMore: Boolean? = null,
    val error: Throwable? = null
) : State {

    data class MineDataVO(
        val articleVO: MineArticleVO,
        val deleteVO: MineDeleteVO
    ) : ListItem

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

    data class MineDeleteVO(
        val id: Int,
        val originId: Int
    ) : ListItem
}