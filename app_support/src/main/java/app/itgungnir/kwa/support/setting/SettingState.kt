package app.itgungnir.kwa.support.setting

import my.itgungnir.rxmvvm.core.mvvm.State
import my.itgungnir.ui.easy_adapter.ListItem

data class SettingState(
    val items: List<ListItem> = listOf(),
    val error: Throwable? = null
) : State {

    object DividerVO : ListItem

    data class CheckableVO(
        val id: Int,
        val iconFont: String,
        val title: String,
        val isChecked: Boolean = false
    ) : ListItem

    data class NavigableVO(
        val id: Int,
        val iconFont: String,
        val title: String
    ) : ListItem

    data class DigitalVO(
        val id: Int,
        val iconFont: String,
        val title: String,
        val digit: String
    ) : ListItem

    object ButtonVO : ListItem
}