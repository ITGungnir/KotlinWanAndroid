package app.itgungnir.kwa.common.util

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import app.itgungnir.kwa.common.redux.AppRedux

class ThemeUtil : Util {

    override fun init(application: Application) {

        when (AppRedux.instance.isDarkMode()) {
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}