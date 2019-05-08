package app.itgungnir.kwa.register

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.http.handleResult
import app.itgungnir.kwa.common.http.io2Main
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

class RegisterViewModel : BaseViewModel<RegisterState>(initialState = RegisterState()) {

    @SuppressLint("CheckResult")
    fun register(userName: String, password: String, confirmPwd: String) {
        HttpClient.api.register(userName, password, confirmPwd)
            .handleResult()
            .io2Main()
            .subscribe({
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