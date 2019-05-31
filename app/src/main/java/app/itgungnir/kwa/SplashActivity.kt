package app.itgungnir.kwa

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import app.itgungnir.kwa.common.MainActivity
import app.itgungnir.kwa.common.SplashActivity
import app.itgungnir.kwa.common.http.io2Main
import app.itgungnir.kwa.common.redux.AppRedux
import app.itgungnir.kwa.common.redux.UpdateVersion
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Single
import my.itgungnir.grouter.annotation.Route
import my.itgungnir.grouter.api.Router
import my.itgungnir.ui.color
import my.itgungnir.ui.dp2px
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
                    }.lparams(ui.ctx.dp2px(140F).toInt(), ui.ctx.dp2px(100F).toInt()) {
                        bottomMargin = ui.ctx.dp2px(50F).toInt()
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

        AppRedux.instance.dispatch(UpdateVersion)

        Single.timer(2L, TimeUnit.SECONDS)
            .io2Main()
            .doOnSubscribe { App.isFirstRun = false }
            .subscribe({
                navigate()
            }, {})
    }

    @SuppressLint("CheckResult")
    private fun requestPermissions() {
        RxPermissions(this).requestEachCombined(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
        ).subscribe { permission ->
            when {
                permission.granted -> navigate()
                permission.shouldShowRequestPermissionRationale -> Unit
                else -> Unit
            }
        }
    }

    private fun navigate() {
        Router.instance.with(this)
            .target(MainActivity)
            .go()
        finish()
    }
}