package app.itgungnir.kwa.common.redux

data class AppState(
    val searchHistory: Set<String> = linkedSetOf()
)