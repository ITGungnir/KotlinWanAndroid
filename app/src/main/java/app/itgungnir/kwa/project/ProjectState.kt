package app.itgungnir.kwa.project

import my.itgungnir.rxmvvm.core.mvvm.State

data class ProjectState(
    val tabs: List<ProjectTabVO> = listOf(),
    val error: Throwable? = null
) : State {

    data class ProjectTabVO(
        val id: Int,
        val name: String
    )
}