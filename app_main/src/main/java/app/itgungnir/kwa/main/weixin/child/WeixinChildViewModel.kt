package app.itgungnir.kwa.main.weixin.child

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.http.handleResult
import app.itgungnir.kwa.common.http.io2Main
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

@SuppressLint("CheckResult")
class WeixinChildViewModel : BaseViewModel<WeixinChildState>(initialState = WeixinChildState()) {

    private var pageNo = 1

    /**
     * 获取
     */
    fun getArticles(cid: Int, k: String) {
        pageNo = 1
        HttpClient.api.weixinArticles(pageNo, cid, k)
            .handleResult()
            .io2Main()
            .map {
                setState {
                    copy(
                        hasMore = !it.over
                    )
                }
                it.datas.map { item ->
                    WeixinChildState.WeixinArticleVO(
                        id = item.id,
                        originId = item.originId,
                        author = item.author,
                        title = item.title,
                        date = item.niceDate,
                        link = item.link
                    )
                }
            }
            .doOnSubscribe {
                setState {
                    copy(
                        refreshing = true,
                        error = null
                    )
                }
            }
            .subscribe({
                pageNo++
                setState {
                    copy(
                        refreshing = false,
                        items = it,
                        shouldScrollToTop = true,
                        error = null
                    )
                }
            }, {
                setState {
                    copy(
                        refreshing = false,
                        error = it
                    )
                }
            })
    }

    /**
     * 加载更多
     */
    fun loadMoreArticles(cid: Int, k: String) {
        HttpClient.api.weixinArticles(pageNo, cid, k)
            .handleResult()
            .io2Main()
            .map {
                setState {
                    copy(
                        hasMore = !it.over
                    )
                }
                it.datas.map { item ->
                    WeixinChildState.WeixinArticleVO(
                        id = item.id,
                        originId = item.originId,
                        author = item.author,
                        title = item.title,
                        date = item.niceDate,
                        link = item.link
                    )
                }
            }
            .doOnSubscribe {
                setState {
                    copy(
                        loading = true,
                        error = null
                    )
                }
            }
            .subscribe({
                pageNo++
                setState {
                    copy(
                        loading = false,
                        items = items.toMutableList() + it,
                        shouldScrollToTop = false,
                        error = null
                    )
                }
            }, {
                setState {
                    copy(
                        loading = false,
                        error = it
                    )
                }
            })
    }
}