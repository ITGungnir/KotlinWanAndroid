package app.itgungnir.kwa.common.widget.flex

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout

class FlexView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FlexboxLayout(context, attrs, defStyleAttr) {

    private var layoutId: Int? = null
    private var render: ((View, Any) -> Unit)? = null

    private val inflater by lazy { LayoutInflater.from(context) }

    init {
        flexDirection = FlexDirection.ROW
        flexWrap = FlexWrap.WRAP

        setShowDivider(SHOW_DIVIDER_MIDDLE)
        setDividerDrawable(FlexDividerDrawable(context))
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> bind(layoutId: Int, render: (view: View, data: T) -> Unit) {
        this.layoutId = layoutId
        this.render = render as (View, Any) -> Unit
    }

    fun <T> refresh(items: List<T>) {
        if (layoutId == null || render == null) {
            return
        }
        if (childCount > 0) {
            removeAllViews()
        }
        items.forEach {
            val view = inflater.inflate(layoutId!!, this, false)
            render?.invoke(view, it as Any)
            this.addView(view)
        }
    }
}