package app.itgungnir.kwa.common.widget.flex

import android.content.Context
import android.graphics.drawable.GradientDrawable
import app.itgungnir.kwa.common.dp2px

class FlexDividerDrawable(context: Context) : GradientDrawable() {

    init {
        setSize(context.dp2px(10.0F), context.dp2px(10.0F))
    }
}