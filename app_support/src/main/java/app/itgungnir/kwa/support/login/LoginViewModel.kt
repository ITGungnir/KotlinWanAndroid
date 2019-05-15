package app.itgungnir.kwa.support.login

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.http.handleResult
import app.itgungnir.kwa.common.http.io2Main
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

class LoginViewModel : BaseViewModel<LoginState>(initialState = LoginState()) {

    @SuppressLint("CheckResult")
    fun login(userName: String, password: String) {
        HttpClient.api.login(userName, password)
            .handleResult()
            .io2Main()
            .subscribe({
                setState {
                    copy(
                        userInfo = LoginState.UserInfoVO(collectIds = it.collectIds, userName = it.username),
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