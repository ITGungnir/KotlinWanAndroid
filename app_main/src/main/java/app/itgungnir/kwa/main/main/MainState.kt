package app.itgungnir.kwa.main.main

import my.itgungnir.rxmvvm.core.mvvm.State

data class MainState(
    val versionInfo: VersionVO? = null,
    val error: Throwable? = null
) : State {

    data class TabItem(
        val title: String,
        val unselectedIcon: String,
        val selectedIcon: String
    )

    data class VersionVO(
        val upgradeUrl: String,
        val upgradeVersion: String,
        val upgradeDesc: String
    )
}