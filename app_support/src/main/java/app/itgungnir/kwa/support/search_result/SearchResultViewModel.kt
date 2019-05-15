package app.itgungnir.kwa.support.search_result

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.http.handleResult
import app.itgungnir.kwa.common.http.io2Main
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

@SuppressLint("CheckResult")
class SearchResultViewModel : BaseViewModel<SearchResultState>(initialState = SearchResultState()) {

    private var pageNo: Int = 0

    fun getSearchResult(k: String) {
        pageNo = 0
        HttpClient.api.searchResult(pageNo, k)
            .handleResult()
            .io2Main()
            .map {
                setState {
                    copy(
                        hasMore = !it.over
                    )
                }
                it.datas.map { item ->
                    SearchResultState.SearchResultArticleVO(
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

    fun loadMoreSearchResult(k: String) {
        HttpClient.api.searchResult(pageNo, k)
            .handleResult()
            .io2Main()
            .map {
                setState {
                    copy(
                        hasMore = !it.over
                    )
                }
                it.datas.map { item ->
                    SearchResultState.SearchResultArticleVO(
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