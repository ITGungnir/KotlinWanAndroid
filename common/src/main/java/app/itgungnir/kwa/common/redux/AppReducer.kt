package app.itgungnir.kwa.common.redux

import my.itgungnir.rxmvvm.core.redux.Action
import my.itgungnir.rxmvvm.core.redux.Reducer

class AppReducer : Reducer<AppState> {

    override fun reduce(state: AppState, action: Action): AppState = when (action) {
        is AddSearchHistory ->
            state.copy(searchHistory = state.searchHistory + action.value)
        is ClearSearchHistory ->
            state.copy(searchHistory = linkedSetOf())
        else ->
            state
    }
}