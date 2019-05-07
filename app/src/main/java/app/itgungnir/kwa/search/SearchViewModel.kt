package app.itgungnir.kwa.search

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.http.handleResult
import app.itgungnir.kwa.common.http.io2Main
import app.itgungnir.kwa.common.redux.AppRedux
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

class SearchViewModel : BaseViewModel<SearchState>(initialState = SearchState()) {

    /**
     * 初始化数据
     */
    @SuppressLint("CheckResult")
    fun initData() {

        val s1 = HttpClient.api.hotKeys()
            .handleResult()
            .io2Main()
            .map { SearchState.SearchHotKeyVO(data = it.map { item -> item.name }) }

        val s2 = Single.just(AppRedux.instance.currState()?.searchHistory)
            .map { SearchState.SearchHistoryVO(data = it.toList()) }

        Single.zip(s1, s2, BiFunction { t1: SearchState.SearchHotKeyVO, t2: SearchState.SearchHistoryVO ->
            listOf(t1, t2)
        }).subscribe({
            setState {
                copy(
                    items = it,
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