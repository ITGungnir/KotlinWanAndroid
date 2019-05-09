package app.itgungnir.kwa.common.redux

import my.itgungnir.rxmvvm.core.redux.Action
import my.itgungnir.rxmvvm.core.redux.Reducer

class AppReducer : Reducer<AppState> {

    override fun reduce(state: AppState, action: Action): AppState = when (action) {
        // 添加搜索历史
        is AddSearchHistory ->
            state.copy(searchHistory = state.searchHistory + action.value)
        // 清空搜索历史
        is ClearSearchHistory ->
            state.copy(searchHistory = linkedSetOf())
        // 持久化Cookies
        is LocalizeCookies ->
            state.copy(cookies = action.cookies)
        // 持久化用户名
        is LocalizeUserInfo ->
            state.copy(collectIds = action.collectIds, userName = action.userName)
        // 清空用户信息
        is ClearUserInfo ->
            state.copy(userName = null, cookies = setOf(), collectIds = setOf())
        // 收藏文章
        is CollectArticle ->
            state.copy(collectIds = state.collectIds + action.articleId)
        // 取消收藏文章
        is DisCollectArticle ->
            state.copy(collectIds = state.collectIds - action.articleId)
        // Default
        else ->
            state
    }
}