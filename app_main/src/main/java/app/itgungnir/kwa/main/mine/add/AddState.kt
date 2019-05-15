package app.itgungnir.kwa.main.mine.add

import my.itgungnir.rxmvvm.core.mvvm.State

data class AddState(
    val succeed: Unit? = null,
    val error: Throwable? = null
) : State