package app.itgungnir.kwa.common.redux

import app.itgungnir.kwa.common.APP_VERSION

data class AppState(
    // 搜索历史
    val searchHistory: Set<String> = linkedSetOf(),
    // 收藏列表
    val collectIds: Set<Int> = setOf(),
    // 标识收藏列表改变
    val collectChanges: String = "",
    // 用户名
    val userName: String? = null,
    // Cookies
    val cookies: Set<String> = setOf(),
    // 是否开启“自动缓存”
    val autoCache: Boolean = true,
    // 是否开启“无图模式”
    val noImage: Boolean = false,
    // 是否开启“夜间模式”
    val darkMode: Boolean = false,
    // 当前版本
    val version: String = APP_VERSION
)