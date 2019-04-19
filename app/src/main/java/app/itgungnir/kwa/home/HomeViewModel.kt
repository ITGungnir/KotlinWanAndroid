package app.itgungnir.kwa.home

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.http.handleResult
import app.itgungnir.kwa.common.http.io2Main
import app.itgungnir.kwa.common.widget.easy_adapter.ListItem
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

@SuppressLint("CheckResult")
class HomeViewModel : BaseViewModel<HomeState>(initialState = HomeState()) {

    private var currPage = 1

    /**
     * 获取数据
     */
    fun getHomeData() {
        currPage = 1

        val s1 = HttpClient.api.banner()
            .handleResult()
            .io2Main()
            .map {
                HomeState.BannerVO(it.map { item ->
                    HomeState.BannerVO.BannerItem(
                        item.imagePath,
                        item.title,
                        item.url
                    )
                })
            }

        val s2 = HttpClient.api.homeArticle(1)
            .handleResult()
            .io2Main()
            .map {
                setState {
                    copy(
                        hasMore = !it.over
                    )
                }
                it.datas.map { item -> HomeState.ArticleVO(item) }
            }

        Single.zip(s1, s2, BiFunction { t1: HomeState.BannerVO, t2: List<HomeState.ArticleVO> ->
            val responseList = mutableListOf<ListItem>(t1)
            responseList.addAll(t2)
            return@BiFunction responseList
        }).doOnSubscribe {
            setState {
                copy(
                    refreshing = true,
                    error = null
                )
            }
        }.subscribe({
            currPage++
            setState {
                copy(
                    refreshing = false,
                    dataList = it,
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
     * 加载更多数据
     */
    fun loadMoreHomeData() {
        HttpClient.api.homeArticle(currPage)
            .handleResult()
            .io2Main()
            .map {
                setState {
                    copy(
                        hasMore = !it.over
                    )
                }
                it.datas.map { item -> HomeState.ArticleVO(item) }
            }.doOnSubscribe {
                setState {
                    copy(
                        loading = true,
                        error = null
                    )
                }
            }.subscribe({
                currPage++
                setState {
                    copy(
                        loading = false,
                        dataList = dataList.toMutableList() + it,
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