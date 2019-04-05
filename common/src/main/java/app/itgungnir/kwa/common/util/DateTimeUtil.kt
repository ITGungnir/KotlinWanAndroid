package app.itgungnir.kwa.common.util

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid

class DateTimeUtil : Util {

    override fun init(application: Application) {
        JodaTimeAndroid.init(application)
    }
}