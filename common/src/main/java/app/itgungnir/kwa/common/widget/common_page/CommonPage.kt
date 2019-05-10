package app.itgungnir.kwa.common.widget.common_page

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import app.itgungnir.kwa.common.R
import app.itgungnir.kwa.common.onAntiShakeClick
import app.itgungnir.kwa.common.widget.status_view.StatusView
import kotlinx.android.synthetic.main.view_common_page.view.*

class CommonPage @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    CoordinatorLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_common_page, this)

        fab.onAntiShakeClick {
            when (val view = statusView.getDelegate(StatusView.Status.SUCCEED)) {
                is RecyclerView -> view.smoothScrollToPosition(0)
                is ScrollView -> view.smoothScrollTo(0, 0)
            }
        }
    }

    fun refreshLayout() = refreshLayout!!

    fun statusView() = statusView!!
}