package app.itgungnir.kwa.tree.tools

import my.itgungnir.rxmvvm.core.mvvm.State
import my.itgungnir.ui.easy_adapter.ListItem

data class ToolsState(
    val items: List<ToolTagVO> = listOf(),
    val error: Throwable? = null
) : State {

    data class ToolTagVO(
        val id: Int,
        val name: String,
        val link: String
    ) : ListItem
}