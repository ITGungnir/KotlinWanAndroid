package app.itgungnir.kwa.project.child

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.http.handleResult
import app.itgungnir.kwa.common.http.io2Main
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

@SuppressLint("CheckResult")
class ProjectChildViewModel : BaseViewModel<ProjectChildState>(initialState = ProjectChildState()) {

    private var pageNo = 0

    /**
     * 获取项目列表
     */
    fun getProjects(cid: Int) {
        pageNo = 0
        HttpClient.api.projectArticles(pageNo, cid)
            .handleResult()
            .io2Main()
            .map {
                setState {
                    copy(
                        hasMore = !it.over
                    )
                }
                it.datas.map { item ->
                    ProjectChildState.ProjectArticleVO(
                        id = item.id,
                        originId = item.originId,
                        cover = item.envelopePic,
                        title = item.title,
                        author = item.author,
                        desc = item.desc,
                        date = item.niceDate,
                        link = item.link,
                        repositoryLink = item.projectLink
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
     * 加载更多项目
     */
    fun loadMoreProjects(cid: Int) {
        HttpClient.api.projectArticles(pageNo, cid)
            .handleResult()
            .io2Main()
            .map {
                setState {
                    copy(
                        hasMore = !it.over
                    )
                }
                it.datas.map { item ->
                    ProjectChildState.ProjectArticleVO(
                        id = item.id,
                        originId = item.originId,
                        cover = item.envelopePic,
                        title = item.title,
                        author = item.author,
                        desc = item.desc,
                        date = item.niceDate,
                        link = item.link,
                        repositoryLink = item.projectLink
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