package app.itgungnir.kwa.support.web

import my.itgungnir.rxmvvm.core.mvvm.State

data class WebState(
    val error: Throwable? = null
) : State