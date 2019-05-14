package app.itgungnir.kwa.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.itgungnir.kwa.App
import app.itgungnir.kwa.common.MainActivity
import app.itgungnir.kwa.common.SplashActivity
import app.itgungnir.kwa.common.http.io2Main
import io.reactivex.Single
import my.itgungnir.grouter.annotation.Route
import my.itgungnir.grouter.api.Router
import org.jetbrains.anko.setContentView
import java.util.concurrent.TimeUnit

@Route(SplashActivity)
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SplashUI().setContentView(this)

        initComponent()
    }

    @SuppressLint("CheckResult")
    private fun initComponent() {
        if (!App.isFirstRun) {
            navigate()
            return
        }

        Single.timer(2L, TimeUnit.SECONDS)
            .io2Main()
            .doOnSubscribe { App.isFirstRun = false }
            .subscribe({
                navigate()
            }, {})
    }

    private fun navigate() {
        Router.instance.with(this)
            .target(MainActivity)
            .go()
        finish()
    }
}