package app.itgungnir.kwa.common

import android.content.Context
import android.util.TypedValue

fun Context.dp2px(dp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
