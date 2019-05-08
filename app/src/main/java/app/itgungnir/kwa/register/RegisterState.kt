package app.itgungnir.kwa.register

import my.itgungnir.rxmvvm.core.mvvm.State

data class RegisterState(
    val succeed: Unit? = null,
    val error: Throwable? = null
) : State