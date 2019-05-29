package app.itgungnir.kwa.common.util

import android.app.Application
import com.tencent.bugly.crashreport.CrashReport

class CrashDetectUtil : Util {

    override fun init(application: Application) {

        CrashReport.initCrashReport(application)
    }
}