package app.itgungnir.kwa.common.util

import android.app.Application

class AppConfig private constructor() {

    companion object {
        val instance by lazy { AppConfig() }
    }

    fun init(application: Application) {
        listOf(
            CacheUtil.instance,
            CrashDetectUtil(),
            DateTimeUtil(),
            LeakDetectUtil(),
            LoggingUtil(),
            ReduxUtil(),
            ScreenAdaptUtil(),
            ThemeUtil()
        ).map {
            it.init(application)
        }
    }
}