package my.itgungnir.kotlin.wan.android

import android.support.multidex.MultiDexApplication
import my.itgungnir.apt.router.annotation.Modules
import my.itgungnir.apt.router.api.Router
import my.itgungnir.kotlin.wan.android.common.util.AppConfig

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