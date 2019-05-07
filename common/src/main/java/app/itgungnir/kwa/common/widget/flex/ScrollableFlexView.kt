package app.itgungnir.kwa.common.widget.flex

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.itgungnir.kwa.common.widget.easy_adapter.ListItem
import app.itgungnir.kwa.common.widget.easy_adapter.bind
import app.itgungnir.kwa.common.widget.easy_adapter.update
import com.google.android.flexbox.*

class ScrollableFlexView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var manager: FlexboxLayoutManager = FlexboxLayoutManager(context).apply {
        flexDirection = FlexDirection.ROW
        flexWrap = FlexWrap.WRAP
    }

    init {
        layoutManager = manager
        addItemDecoration(FlexboxItemDecoration(context).apply {
            this.setDrawable(FlexDividerDrawable(context))
            this.setOrientation(FlexboxItemDecoration.BOTH)
        })
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : ListItem> bind(layoutId: Int, render: (view: View, data: T) -> Unit) {
        bind(manager = manager, delegate = FlexDelegate(layoutId, render))
    }

    fun <T : ListItem> refresh(items: List<T>) {
        update(items)
    }
}