package app.itgungnir.kwa.tree

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.http.handleResult
import app.itgungnir.kwa.common.http.io2Main
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

class TreeViewModel : BaseViewModel<TreeState>(initialState = TreeState()) {

    /**
     * 获取知识体系
     */
    @SuppressLint("CheckResult")
    fun getTreeList() {
        HttpClient.api.hierarchyTabs()
            .handleResult()
            .io2Main()
            .doOnSubscribe {
                setState {
                    copy(
                        refreshing = true,
                        error = null
                    )
                }
            }
            .subscribe({
                setState {
                    copy(
                        refreshing = false,
                        items = it.map { item ->
                            TreeState.TreeVO(
                                name = item.name,
                                children = item.children.map { child ->
                                    TreeState.TreeVO.TreeTagVO(id = child.id, name = child.name)
                                }
                            )
                        },
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
}