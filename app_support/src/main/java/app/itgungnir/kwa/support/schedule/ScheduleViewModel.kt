package app.itgungnir.kwa.support.schedule

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.http.handleResult
import app.itgungnir.kwa.common.http.io2Main
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

@SuppressLint("CheckResult")
class ScheduleViewModel : BaseViewModel<ScheduleState>(initialState = ScheduleState()) {

    private var pageNo: Int = 1

    fun getScheduleList() {
        withState {
            getScheduleList(it.type, it.priority, it.orderBy)
        }
    }

    fun getScheduleList(type: Int?, priority: Int?, orderBy: Int) {
        pageNo = 1
        HttpClient.api.scheduleList(page = pageNo, status = 0, type = type, priority = priority, orderBy = orderBy)
            .handleResult()
            .io2Main()
            .map {
                setState {
                    copy(
                        hasMore = !it.over
                    )
                }
                it.datas.map { item ->
                    ScheduleState.ScheduleVO(
                        id = item.id,
                        content = item.content,
                        title = item.title,
                        targetDate = item.dateStr,
                        type = item.type,
                        priority = item.priority
                    )
                }
            }
            .doOnSubscribe {
                setState {
                    copy(
                        refreshing = true,
                        error = null
                    )
                }
            }
            .subscribe({
                pageNo++
                setState {
                    copy(
                        refreshing = false,
                        items = it,
                        error = null
                    )
                }
            }, {
                setState {
                    copy(
                        refreshing = false,
                        error = it
                    )
                }
            })
    }

    fun loadMoreScheduleList() {
        withState {
            loadMoreScheduleList(it.type, it.priority, it.orderBy)
        }
    }

    private fun loadMoreScheduleList(type: Int?, priority: Int?, orderBy: Int) {
        HttpClient.api.scheduleList(page = pageNo, status = 0, type = type, priority = priority, orderBy = orderBy)
            .handleResult()
            .io2Main()
            .map {
                setState {
                    copy(
                        hasMore = !it.over
                    )
                }
                it.datas.map { item ->
                    ScheduleState.ScheduleVO(
                        id = item.id,
                        content = item.content,
                        title = item.title,
                        targetDate = item.dateStr,
                        type = item.type,
                        priority = item.priority
                    )
                }
            }
            .doOnSubscribe {
                setState {
                    copy(
                        loading = true,
                        error = null
                    )
                }
            }
            .subscribe({
                pageNo++
                setState {
                    copy(
                        loading = false,
                        items = items.toMutableList() + it,
                        error = null
                    )
                }
            }, {
                setState {
                    copy(
                        loading = false,
                        error = it
                    )
                }
            })
    }

    fun addSchedule(title: String, content: String, date: String, type: Int, priority: Int) {
        HttpClient.api.addSchedule(title, content, date, type, priority)
            .handleResult()
            .io2Main()
            .subscribe({
                setState {
                    copy(
                        dismissFlag = Unit,
                        error = null
                    )
                }
            }, {
                setState {
                    copy(
                        error = it
                    )
                }
            })
    }

    fun editSchedule(id: Int, title: String, content: String, date: String, type: Int, priority: Int) {
        HttpClient.api.editSchedule(id, title, content, date, 0, type, priority)
            .handleResult()
            .io2Main()
            .subscribe({
                setState {
                    copy(
                        items = items.map { item ->
                            if (item.id == id) {
                                item.copy(
                                    title = title,
                                    content = content,
                                    targetDate = date,
                                    type = type,
                                    priority = priority
                                )
                            } else {
                                item.copy()
                            }
                        },
                        dismissFlag = Unit,
                        error = null
                    )
                }
            }, {
                setState {
                    copy(
                        error = it
                    )
                }
            })
    }

    fun finishSchedule(position: Int, id: Int) {
        HttpClient.api.finishSchedule(id, 1)
            .handleResult()
            .io2Main()
            .subscribe({
                setState {
                    copy(
                        items = items.toMutableList() - items[position],
                        dismissFlag = Unit,
                        error = null
                    )
                }
            }, {
                setState {
                    copy(
                        error = it
                    )
                }
            })
    }

    fun deleteSchedule(position: Int, id: Int) {
        HttpClient.api.deleteSchedule(id)
            .handleResult()
            .io2Main()
            .subscribe({
                setState {
                    copy(
                        items = items.toMutableList() - items[position],
                        error = null
                    )
                }
            }, {
                setState {
                    copy(
                        error = it
                    )
                }
            })
    }
}