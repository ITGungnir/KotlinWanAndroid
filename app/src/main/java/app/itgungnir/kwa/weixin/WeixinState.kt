package app.itgungnir.kwa.weixin

import my.itgungnir.rxmvvm.core.mvvm.State

data class WeixinState(
    val error: Throwable? = null
) : State