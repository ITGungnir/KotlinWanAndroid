package app.itgungnir.kwa.login

import my.itgungnir.rxmvvm.core.mvvm.State

data class LoginState(
    val succeed: Unit? = null,
    val error: Throwable? = null
) : State