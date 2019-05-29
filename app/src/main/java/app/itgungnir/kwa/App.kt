package app.itgungnir.kwa

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import app.itgungnir.kwa.common.util.AppConfig

class App : MultiDexApplication() {

    companion object {
        // 是否是第一次运行项目，通过判断决定是否跳过SplashActivity
        var isFirstRun = true
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        AppConfig.instance.init(this)
    }
}