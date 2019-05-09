package app.itgungnir.kwa.tree.tools

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.http.handleResult
import app.itgungnir.kwa.common.http.io2Main
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

class ToolsViewModel : BaseViewModel<ToolsState>(initialState = ToolsState()) {

    @SuppressLint("CheckResult")
    fun getTools() {
        HttpClient.api.tools()
            .handleResult()
            .io2Main()
            .map {
                it.map { item ->
                    ToolsState.ToolTagVO(
                        id = item.id,
                        name = item.name,
                        link = item.link
                    )
                }
            }
            .subscribe({
                setState {
                    copy(
                        items = it,
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