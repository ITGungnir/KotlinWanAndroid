package app.itgungnir.kwa.login

import my.itgungnir.rxmvvm.core.mvvm.State

data class LoginState(
    val userInfo: UserInfoVO? = null,
    val error: Throwable? = null
) : State {

    data class UserInfoVO(
        val collectIds: Set<Int>,
        val userName: String
    )
}