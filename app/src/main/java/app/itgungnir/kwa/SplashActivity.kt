package app.itgungnir.kwa

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import app.itgungnir.kwa.common.MainActivity
import app.itgungnir.kwa.common.SplashActivity
import app.itgungnir.kwa.common.color
import app.itgungnir.kwa.common.dp2px
import app.itgungnir.kwa.common.http.io2Main
import io.reactivex.Single
import my.itgungnir.grouter.annotation.Route
import my.itgungnir.grouter.api.Router
import org.jetbrains.anko.*
import java.util.concurrent.TimeUnit

@Route(SplashActivity)
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        object : AnkoComponent<SplashActivity> {
            override fun createView(ui: AnkoContext<SplashActivity>): View = with(ui) {
                verticalLayout {
                    backgroundColor = this@SplashActivity.color(R.color.colorPure)
                    imageView {
                        imageResource = R.mipmap.img_placeholder
                        scaleType = ImageView.ScaleType.CENTER_INSIDE
                    }.lparams(ui.ctx.dp2px(140F), ui.ctx.dp2px(100F)) {
                        bottomMargin = ui.ctx.dp2px(50F)
                    }
                }.apply {
                    gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
                }
            }
        }.setContentView(this)

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