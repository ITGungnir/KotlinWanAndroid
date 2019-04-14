package app.itgungnir.kwa.structure

import my.itgungnir.rxmvvm.core.mvvm.State

data class StructureState(
    val error: Throwable? = null
) : State