package app.itgungnir.kwa.project

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.http.handleResult
import app.itgungnir.kwa.common.http.io2Main
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

class ProjectViewModel : BaseViewModel<ProjectState>(initialState = ProjectState()) {

    /**
     * 获取项目分类
     */
    @SuppressLint("CheckResult")
    fun getProjectTabs() {
        HttpClient.api.projectTabs()
            .handleResult()
            .io2Main()
            .map { it.map { item -> ProjectState.ProjectTabVO(item.id, item.name) } }
            .subscribe({
                setState {
                    copy(
                        tabs = it,
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