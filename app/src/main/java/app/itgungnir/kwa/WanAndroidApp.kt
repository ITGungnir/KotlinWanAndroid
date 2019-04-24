package app.itgungnir.kwa

import androidx.multidex.MultiDexApplication
import app.itgungnir.kwa.common.util.AppConfig
import my.itgungnir.apt.router.annotation.Modules
import my.itgungnir.apt.router.api.Router

@Modules("app")
class WanAndroidApp : MultiDexApplication() {

    companion object {
        // 是否是第一次运行项目，通过判断决定是否跳过SplashActivity
        var isFirstRun = true
    }

    override fun onCreate() {
        super.onCreate()

        Router.instance.init(this)

        AppConfig.instance.init(this)
    }
}