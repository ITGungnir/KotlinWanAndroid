package app.itgungnir.kwa.common.util

import android.app.Application
import app.itgungnir.kwa.common.BuildConfig
import app.itgungnir.kwa.common.HTTP_LOG_TAG
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

class LoggingUtil : Util {

    override fun init(application: Application) {

        val logStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodOffset(4)
            .tag(HTTP_LOG_TAG)
            .build()

        Logger.addLogAdapter(object : AndroidLogAdapter(logStrategy) {
            override fun isLoggable(priority: Int, tag: String?) = BuildConfig.DEBUG
        })
    }
}