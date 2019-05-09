package app.itgungnir.kwa.mine.add

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.http.handleResult
import app.itgungnir.kwa.common.http.io2Main
import app.itgungnir.kwa.common.redux.AppRedux
import app.itgungnir.kwa.common.redux.CollectArticle
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

class AddViewModel : BaseViewModel<AddState>(initialState = AddState()) {

    /**
     * 收藏站外文章
     */
    @SuppressLint("CheckResult")
    fun addArticle(title: String, link: String) {
        HttpClient.api.outerCollect(title, "站外收藏", link)
            .handleResult()
            .io2Main()
            .subscribe({
                AppRedux.instance.dispatch(CollectArticle(it.id))
                setState {
                    copy(
                        succeed = Unit,
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