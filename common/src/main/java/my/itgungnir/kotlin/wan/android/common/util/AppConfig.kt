package my.itgungnir.kotlin.wan.android.common.util

import android.app.Application

class AppConfig private constructor() {

    companion object {
        val instance by lazy { AppConfig() }
    }

    fun init(application: Application) {
        listOf(
            DateTimeUtil(),
            LeakDetectUtil()
        ).map {
            it.init(application)
        }
    }
}