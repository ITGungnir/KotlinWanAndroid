package my.itgungnir.kotlin.wan.android.common.ext

import android.content.Context
import android.util.TypedValue

fun Context.dp2px(dp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
