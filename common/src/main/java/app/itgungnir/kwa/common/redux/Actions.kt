package app.itgungnir.kwa.common.redux

import my.itgungnir.rxmvvm.core.redux.Action

data class AddSearchHistory(
    val value: String
) : Action

object ClearSearchHistory : Action