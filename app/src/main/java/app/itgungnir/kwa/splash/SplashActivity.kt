package app.itgungnir.kwa.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.itgungnir.kwa.WanAndroidApp
import app.itgungnir.kwa.common.http.io2Main
import io.reactivex.Single
import my.itgungnir.apt.router.annotation.Route
import my.itgungnir.apt.router.api.Router
import org.jetbrains.anko.setContentView
import java.util.concurrent.TimeUnit

@Route("splash")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SplashUI().setContentView(this)

        initComponent()
    }

    @SuppressLint("CheckResult")
    private fun initComponent() {
        if (!WanAndroidApp.isFirstRun) {
            navigate()
            return
        }

        Single.timer(2L, TimeUnit.SECONDS)
            .io2Main()
            .doOnSubscribe { WanAndroidApp.isFirstRun = false }
            .subscribe({
                navigate()
            }, {})
    }

    private fun navigate() {
        Router.instance.with(this).target("main").go()
        finish()
    }
}