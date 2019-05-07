package app.itgungnir.kwa.tree.tools

import app.itgungnir.kwa.common.widget.easy_adapter.ListItem
import my.itgungnir.rxmvvm.core.mvvm.State

data class ToolsState(
    val items: List<ToolTagVO> = listOf(),
    val error: Throwable? = null
) : State {

    data class ToolTagVO(
        val name: String,
        val link: String
    ) : ListItem
}