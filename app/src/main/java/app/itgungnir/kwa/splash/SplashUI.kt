package app.itgungnir.kwa.splash

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.util.dp2px
import org.jetbrains.anko.*

class SplashUI : AnkoComponent<SplashActivity> {

    override fun createView(ui: AnkoContext<SplashActivity>): View = with(ui) {
        verticalLayout {
            backgroundColor = Color.WHITE
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
}