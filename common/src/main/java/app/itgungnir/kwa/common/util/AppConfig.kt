package app.itgungnir.kwa.common.util

import android.app.Application

class AppConfig private constructor() {

    companion object {
        val instance by lazy { AppConfig() }
    }

    fun init(application: Application) {
        listOf(
            DateTimeUtil(),
            LeakDetectUtil(),
            LoggingUtil()
        ).map {
            it.init(application)
        }
    }
}