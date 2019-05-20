package app.itgungnir.kwa.main.home

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.http.handleResult
import app.itgungnir.kwa.common.http.io2Main
import io.reactivex.Single
import io.reactivex.functions.Function3
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel
import my.itgungnir.ui.easy_adapter.ListItem

@SuppressLint("CheckResult")
class HomeViewModel : BaseViewModel<HomeState>(initialState = HomeState()) {

    private var pageNo = 0

    /**
     * 获取数据
     */
    fun getHomeData() {
        pageNo = 0

        val s1 = HttpClient.api.banners()
            .handleResult()
            .io2Main()
            .map {
                HomeState.BannerVO(it.map { item ->
                    HomeState.BannerVO.BannerItem(
                        id = item.id,
                        url = item.imagePath,
                        title = item.title,
                        target = item.url
                    )
                })
            }

        val s2 = HttpClient.api.topArticles()
            .handleResult()
            .io2Main()
            .map {
                it.map { item ->
                    HomeState.HomeArticleVO(
                        id = item.id,
                        originId = item.originId,
                        author = item.author,
                        category = "${item.superChapterName} / ${item.chapterName}",
                        categoryId = item.chapterId,
                        title = item.title,
                        date = item.niceDate,
                        link = item.link,
                        isTop = true
                    )
                }
            }

        val s3 = HttpClient.api.homeArticles(pageNo)
            .handleResult()
            .io2Main()
            .map {
                setState {
                    copy(
                        hasMore = !it.over
                    )
                }
                it.datas.map { item ->
                    HomeState.HomeArticleVO(
                        id = item.id,
                        originId = item.originId,
                        author = item.author,
                        category = "${item.superChapterName} / ${item.chapterName}",
                        categoryId = item.chapterId,
                        title = item.title,
                        date = item.niceDate,
                        link = item.link
                    )
                }
            }

        Single.zip(
            s1, s2, s3,
            Function3 { t1: HomeState.BannerVO, t2: List<HomeState.HomeArticleVO>, t3: List<HomeState.HomeArticleVO> ->
                val responseList = mutableListOf<ListItem>(t1)
                responseList.addAll(t2)
                responseList.addAll(t3)
                return@Function3 responseList
            }
        ).doOnSubscribe {
            setState {
                copy(
                    refreshing = true,
                    error = null
                )
            }
        }.subscribe({
            pageNo++
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
        HttpClient.api.homeArticles(pageNo)
            .handleResult()
            .io2Main()
            .map {
                setState {
                    copy(
                        hasMore = !it.over
                    )
                }
                it.datas.map { item ->
                    HomeState.HomeArticleVO(
                        id = item.id,
                        originId = item.originId,
                        author = item.author,
                        category = "${item.superChapterName} / ${item.chapterName}",
                        categoryId = item.chapterId,
                        title = item.title,
                        date = item.niceDate,
                        link = item.link
                    )
                }
            }.doOnSubscribe {
                setState {
                    copy(
                        loading = true,
                        error = null
                    )
                }
            }.subscribe({
                pageNo++
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