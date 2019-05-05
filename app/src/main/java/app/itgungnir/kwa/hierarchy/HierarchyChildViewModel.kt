package app.itgungnir.kwa.hierarchy

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.http.handleResult
import app.itgungnir.kwa.common.http.io2Main
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

@SuppressLint("CheckResult")
class HierarchyChildViewModel : BaseViewModel<HierarchyChildState>(initialState = HierarchyChildState()) {

    private var pageNo = 0

    /**
     * 获取
     */
    fun getArticles(cid: Int) {
        pageNo = 0
        HttpClient.api.hierarchy(pageNo, cid)
            .handleResult()
            .io2Main()
            .map {
                setState {
                    copy(
                        hasMore = !it.over
                    )
                }
                it.datas.map { item ->
                    HierarchyChildState.ArticleVO(
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
    fun loadMoreArticles(cid: Int) {
        HttpClient.api.hierarchy(pageNo, cid)
            .handleResult()
            .io2Main()
            .map {
                setState {
                    copy(
                        hasMore = !it.over
                    )
                }
                it.datas.map { item ->
                    HierarchyChildState.ArticleVO(
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