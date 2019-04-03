package my.itgungnir.kotlin.wan.android.splash

import android.arch.lifecycle.Observer
import my.itgungnir.apt.router.annotation.Route
import my.itgungnir.apt.router.api.Router
import my.itgungnir.kotlin.wan.android.WanAndroidApp
import my.itgungnir.rxmvvm.core.ext.createVM
import my.itgungnir.rxmvvm.core.mvvm.BaseActivity

@Route("splash")
class SplashActivity : BaseActivity<SplashVM>() {

    override fun contentView(): SplashUI = SplashUI()

    override fun obtainVM(): SplashVM = createVM()

    override fun initComponent() {
        if (!WanAndroidApp.isFirstRun) {
            navigate()
            return
        }
        WanAndroidApp.isFirstRun = false
        vm?.startCountDown()
    }

    override fun observeVM() {
        vm?.countDownState?.observe(this, Observer {
            navigate()
        })
    }

    private fun navigate() {
        Router.instance.with(this).target("main").go()
        finish()
    }
}