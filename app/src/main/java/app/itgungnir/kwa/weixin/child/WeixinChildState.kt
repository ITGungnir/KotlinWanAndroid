package app.itgungnir.kwa.weixin.child

import app.itgungnir.kwa.common.widget.easy_adapter.ListItem
import my.itgungnir.rxmvvm.core.mvvm.State

data class WeixinChildState(
    val refreshing: Boolean = false,
    val items: List<WeixinArticleVO> = listOf(),
    val loading: Boolean = false,
    val hasMore: Boolean = false,
    val shouldScrollToTop: Boolean = false,
    val error: Throwable? = null
) : State {

    data class WeixinArticleVO(
        val id: Int,
        val originId: Int,
        val author: String,
        val title: String,
        val date: String,
        val link: String
    ) : ListItem
}