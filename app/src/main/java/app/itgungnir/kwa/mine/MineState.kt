package app.itgungnir.kwa.mine

import my.itgungnir.rxmvvm.core.mvvm.State

data class MineState(
    val error: Throwable? = null
) : State