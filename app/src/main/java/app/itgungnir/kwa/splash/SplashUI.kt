package app.itgungnir.kwa.splash

import android.arch.lifecycle.LifecycleOwner
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.ext.dp2px
import my.itgungnir.rxmvvm.core.mvvm.BaseUI
import org.jetbrains.anko.*

class SplashUI : BaseUI() {

    override fun createView(ui: AnkoContext<LifecycleOwner>): View = with(ui) {
        verticalLayout {
            backgroundColor = Color.WHITE
            imageView {
                imageResource = R.drawable.img_placeholder
                scaleType = ImageView.ScaleType.CENTER_INSIDE
            }.lparams(ui.ctx.dp2px(140F), ui.ctx.dp2px(100F)) {
                bottomMargin = ui.ctx.dp2px(50F)
            }
        }.apply {
            gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
        }
    }
}