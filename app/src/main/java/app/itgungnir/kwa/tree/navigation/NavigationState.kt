package app.itgungnir.kwa.tree.navigation

import app.itgungnir.kwa.common.widget.easy_adapter.ListItem
import my.itgungnir.rxmvvm.core.mvvm.State

data class NavigationState(
    val items: List<NavigationVO> = listOf(),
    val error: Throwable? = null
) : State {

    data class NavigationVO(
        val title: String,
        val children: List<NavTagVO> = listOf(),
        val selected: Boolean = false
    ) : ListItem {

        data class NavTagVO(
            val name: String,
            val link: String
        ) : ListItem
    }
}