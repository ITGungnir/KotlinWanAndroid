package app.itgungnir.kwa.support.schedule.done

import app.itgungnir.kwa.support.schedule.ScheduleState
import my.itgungnir.rxmvvm.core.mvvm.State

data class ScheduleDoneState(
    val refreshing: Boolean = false,
    val items: List<ScheduleState.ScheduleVO> = listOf(),
    val loading: Boolean = false,
    val hasMore: Boolean = false,
    val error: Throwable? = null
) : State