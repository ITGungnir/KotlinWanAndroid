package app.itgungnir.kwa.tree

import app.itgungnir.kwa.common.widget.easy_adapter.ListItem
import my.itgungnir.rxmvvm.core.mvvm.State

data class TreeState(
    val refreshing: Boolean = false,
    val items: List<TreeVO> = listOf(),
    val error: Throwable? = null
) : State {

    data class TreeVO(
        val name: String,
        val children: List<TreeChildVO>
    ) : ListItem {

        data class TreeChildVO(
            val id: Int,
            val name: String
        )
    }
}