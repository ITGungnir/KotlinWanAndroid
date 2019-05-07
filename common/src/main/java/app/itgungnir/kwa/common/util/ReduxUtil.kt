package app.itgungnir.kwa.common.util

import android.app.Application
import app.itgungnir.kwa.common.redux.AppRedux

class ReduxUtil : Util {

    override fun init(application: Application) {
        AppRedux.init(application)
    }
}