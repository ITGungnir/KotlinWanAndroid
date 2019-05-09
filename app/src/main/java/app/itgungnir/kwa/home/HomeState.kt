package app.itgungnir.kwa.home

import app.itgungnir.kwa.common.widget.easy_adapter.ListItem
import my.itgungnir.rxmvvm.core.mvvm.State

data class HomeState(
    val refreshing: Boolean = false,
    val dataList: List<ListItem> = listOf(),
    val loading: Boolean = false,
    val hasMore: Boolean = false,
    val error: Throwable? = null
) : State {

    data class BannerVO(
        val items: List<BannerItem> = listOf()
    ) : ListItem {

        data class BannerItem(
            val id: Int,
            val url: String,
            val title: String,
            val target: String
        )
    }

    data class HomeArticleVO(
        val id: Int,
        val originId: Int,
        val author: String,
        val category: String,
        val categoryId: Int,
        val title: String,
        val date: String,
        val link: String,
        val isTop: Boolean = false
    ) : ListItem
}