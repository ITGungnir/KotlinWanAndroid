package app.itgungnir.kwa.support.register

import my.itgungnir.rxmvvm.core.mvvm.State

data class RegisterState(
    val succeed: Unit? = null,
    val error: Throwable? = null
) : State