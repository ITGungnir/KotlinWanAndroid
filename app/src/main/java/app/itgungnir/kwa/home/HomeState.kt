package app.itgungnir.kwa.home

import app.itgungnir.kwa.common.http.dto.HomeArticleResponse
import app.itgungnir.kwa.common.widget.banner.BannerItem
import app.itgungnir.kwa.common.widget.recycler_list.ItemData
import my.itgungnir.rxmvvm.core.mvvm.State

data class HomeState(
    val refreshing: Boolean = false,
    val dataList: List<ItemData> = listOf(),
    val loading: Boolean = false,
    val hasMore: Boolean = false,
    val error: Throwable? = null
) : State {

    data class BannerVO(
        val items: List<BannerItem> = listOf()
    ) : ItemData

    data class ArticleVO(
        val item: HomeArticleResponse.Item
    ) : ItemData
}