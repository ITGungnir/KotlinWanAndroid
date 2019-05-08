package app.itgungnir.kwa.common.redux

data class AppState(
    // 搜索历史
    val searchHistory: Set<String> = linkedSetOf(),
    // 用户名
    val userName: String? = null,
    // Cookies
    val cookies: Set<String> = setOf()
)