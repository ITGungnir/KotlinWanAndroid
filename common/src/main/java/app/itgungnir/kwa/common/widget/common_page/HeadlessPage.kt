package app.itgungnir.kwa.common.widget.common_page

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView
import app.itgungnir.kwa.common.R
import app.itgungnir.kwa.common.widget.status_view.StatusView
import kotlinx.android.synthetic.main.view_headless_page.view.*

class HeadlessPage @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    CoordinatorLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_headless_page, this)

        fab.setOnClickListener {
            val view = statusView.getDelegate(StatusView.Status.SUCCEED)
            when (view) {
                is RecyclerView -> view.smoothScrollToPosition(0)
                is ScrollView -> view.smoothScrollTo(0, 0)
            }
        }
    }

    fun refreshLayout() = refreshLayout!!

    fun statusView() = statusView!!
}