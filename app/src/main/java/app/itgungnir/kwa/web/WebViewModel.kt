package app.itgungnir.kwa.web

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.http.handleResult
import app.itgungnir.kwa.common.http.io2Main
import app.itgungnir.kwa.common.redux.AppRedux
import app.itgungnir.kwa.common.redux.CollectArticle
import app.itgungnir.kwa.common.redux.DisCollectArticle
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

@SuppressLint("CheckResult")
class WebViewModel : BaseViewModel<WebState>(initialState = WebState()) {

    /**
     * 收藏站内文章
     */
    fun collectInnerArticle(articleId: Int) {
        HttpClient.api.innerCollect(articleId)
            .handleResult()
            .io2Main()
            .subscribe({
                AppRedux.instance.dispatch(CollectArticle(articleId))
                setState {
                    copy(
                        error = null
                    )
                }
            }, {
                setState {
                    copy(
                        error = it
                    )
                }
            })
    }

    /**
     * 收藏站外文章
     */
    fun collectOuterArticle(title: String, link: String) {
        HttpClient.api.outerCollect(title, "站外收藏", link)
            .handleResult()
            .io2Main()
            .subscribe({
                AppRedux.instance.dispatch(CollectArticle(it.id))
                setState {
                    copy(
                        error = null
                    )
                }
            }, {
                setState {
                    copy(
                        error = it
                    )
                }
            })
    }

    /**
     * 取消收藏站内文章
     */
    fun disCollectInnerArticle(articleId: Int) {
        HttpClient.api.innerDisCollect(articleId)
            .handleResult()
            .io2Main()
            .subscribe({
                AppRedux.instance.dispatch(DisCollectArticle(articleId))
                setState {
                    copy(
                        error = null
                    )
                }
            }, {
                setState {
                    copy(
                        error = it
                    )
                }
            })
    }

    /**
     * 取消收藏站外文章
     */
    fun disCollectOuterArticle(articleId: Int, originId: Int) {
        HttpClient.api.outerDisCollect(articleId, originId)
            .handleResult()
            .io2Main()
            .subscribe({
                AppRedux.instance.dispatch(DisCollectArticle(articleId))
                setState {
                    copy(
                        error = null
                    )
                }
            }, {
                setState {
                    copy(
                        error = it
                    )
                }
            })
    }
}