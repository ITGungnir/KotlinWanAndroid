package app.itgungnir.kwa.common

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import app.itgungnir.kwa.common.util.GlideApp

fun Context.dp2px(dp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

fun ImageView.load(url: String) =
    GlideApp.with(this.context)
        .load(url)
        .placeholder(R.mipmap.img_placeholder)
        .error(R.mipmap.img_placeholder)
        .centerCrop()
        .into(this)
