package app.itgungnir.kwa.support.schedule

import my.itgungnir.rxmvvm.core.mvvm.State
import my.itgungnir.ui.easy_adapter.ListItem

data class ScheduleState(
    val refreshing: Boolean = false,
    val type: Int? = null,
    val priority: Int? = null,
    val orderBy: Int = 4,
    val items: List<ScheduleVO> = listOf(),
    val loading: Boolean = false,
    val hasMore: Boolean = false,
    val error: Throwable? = null
) : State {

    data class ScheduleVO(
        val id: Int,
        val title: String,
        val content: String,
        val targetDate: String,
        val type: Int,
        val priority: Int
    ) : ListItem

    data class MenuTabVO(
        val title: String,
        val value: Int?,
        val selected: Boolean = false
    ) : ListItem
}