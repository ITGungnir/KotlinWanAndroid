package app.itgungnir.kwa.common.widget.flex

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import app.itgungnir.kwa.common.dp2px
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout

class FlexView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FlexboxLayout(context, attrs, defStyleAttr) {

    private val dividerDrawable by lazy {
        GradientDrawable().apply {
            setSize(context.dp2px(10.0F), context.dp2px(5.0F))
        }
    }

    init {
        flexDirection = FlexDirection.ROW
        flexWrap = FlexWrap.WRAP

        setShowDivider(SHOW_DIVIDER_MIDDLE)
        setDividerDrawable(dividerDrawable)
    }
}