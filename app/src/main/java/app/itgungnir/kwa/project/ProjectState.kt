package app.itgungnir.kwa.project

import my.itgungnir.rxmvvm.core.mvvm.State

data class ProjectState(
    val error: Throwable? = null
) : State