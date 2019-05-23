package app.itgungnir.kwa.common.redux

import app.itgungnir.kwa.common.APP_VERSION
import my.itgungnir.rxmvvm.core.redux.Action
import my.itgungnir.rxmvvm.core.redux.Reducer
import java.util.*

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
            state.copy(collectIds = action.collectIds, userName = action.userName, collectChanges = uuid())
        // 清空用户信息
        is ClearUserInfo ->
            state.copy(userName = null, cookies = setOf(), collectIds = setOf(), collectChanges = uuid())
        // 收藏文章
        is CollectArticle ->
            state.copy(collectIds = state.collectIds + action.articleId, collectChanges = uuid())
        // 取消收藏文章
        is DisCollectArticle ->
            state.copy(collectIds = state.collectIds - action.articleId, collectChanges = uuid())
        // 切换“自动缓存”的状态
        is ToggleAutoCache ->
            state.copy(autoCache = !state.autoCache)
        // 切换“无图模式”的状态
        is ToggleNoImage ->
            state.copy(noImage = !state.noImage)
        // 切换“夜间模式”的状态
        is ToggleDarkMode ->
            state.copy(darkMode = !state.darkMode)
        // 更新应用版本信息
        is UpdateVersion ->
            state.copy(version = APP_VERSION)
        // Default
        else ->
            state
    }

    private fun uuid(): String = UUID.randomUUID().toString()
}