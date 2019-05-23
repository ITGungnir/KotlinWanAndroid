package app.itgungnir.kwa.common.util

import android.app.Application

class AppConfig private constructor() {

    companion object {
        val instance by lazy { AppConfig() }
    }

    fun init(application: Application) {
        listOf(
            ScreenAdaptUtil(),
            DateTimeUtil(),
            LeakDetectUtil(),
            LoggingUtil(),
            ReduxUtil(),
            ThemeUtil(),
            CacheUtil.instance
        ).map {
            it.init(application)
        }
    }
}