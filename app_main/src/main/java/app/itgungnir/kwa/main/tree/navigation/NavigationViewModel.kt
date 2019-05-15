package app.itgungnir.kwa.main.tree.navigation

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.http.handleResult
import app.itgungnir.kwa.common.http.io2Main
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

class NavigationViewModel : BaseViewModel<NavigationState>(initialState = NavigationState()) {

    @SuppressLint("CheckResult")
    fun getNavigationList() {
        HttpClient.api.navigation()
            .handleResult()
            .io2Main()
            .subscribe({
                setState {
                    copy(
                        tabs = it.mapIndexed { index, item ->
                            NavigationState.NavTabVO(
                                name = item.name,
                                selected = index == 0
                            )
                        },
                        items = it.map { item ->
                            NavigationState.NavigationVO(
                                title = item.name,
                                children = item.articles.map { data ->
                                    NavigationState.NavigationVO.NavTagVO(
                                        id = data.id,
                                        originId = data.originId,
                                        name = data.title,
                                        link = data.link
                                    )
                                }
                            )
                        },
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