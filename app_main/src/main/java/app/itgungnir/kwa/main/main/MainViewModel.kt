package app.itgungnir.kwa.main.main

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.http.handleResult
import app.itgungnir.kwa.common.http.io2Main
import app.itgungnir.kwa.common.redux.AppRedux
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

class MainViewModel : BaseViewModel<MainState>(initialState = MainState()) {

    @SuppressLint("CheckResult")
    fun getLatestVersion() {
        HttpClient.api2.versionInfo()
            .handleResult()
            .io2Main()
            .subscribe({
                if (it.version > AppRedux.instance.currState().version) {
                    setState {
                        copy(
                            versionInfo = MainState.VersionVO(
                                upgradeUrl = it.downloadUrl,
                                upgradeVersion = it.version,
                                upgradeDesc = it.versionDesc
                            ),
                            error = null
                        )
                    }
                }
            }, {
                setState {
                    copy(
                        versionInfo = null,
                        error = it
                    )
                }
            })
    }
}