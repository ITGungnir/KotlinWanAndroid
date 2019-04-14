package app.itgungnir.kwa.common.widget.status_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout

class StatusView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    private val delegates: HashMap<Status, View> = hashMapOf()

    private var currStatus: Status? = null

    enum class Status { LOADING, SUCCEED, EMPTY, FAILED }

    fun addDelegate(status: Status, layoutId: Int, init: (View) -> Unit): StatusView {
        val view = LayoutInflater.from(context).inflate(layoutId, this, false)
        delegates[status] = view.apply {
            init(this)
            this.visibility = View.GONE
        }
        addView(view)
        return this
    }

    fun loading(block: (View) -> Unit) {
        toggleViews(Status.LOADING)
        delegates[currStatus]?.let { block(it) }
    }

    fun succeed(block: (View) -> Unit) {
        toggleViews(Status.SUCCEED)
        delegates[currStatus]?.let { block(it) }
    }

    fun empty(block: (View) -> Unit) {
        toggleViews(Status.EMPTY)
        delegates[currStatus]?.let { block(it) }
    }

    fun failed(block: (View) -> Unit) {
        toggleViews(Status.FAILED)
        delegates[currStatus]?.let { block(it) }
    }

    fun getDelegate(status: Status) = delegates[status]

    private fun toggleViews(newStatus: Status) {
        if (newStatus == currStatus) {
            return
        }
        Status.values().forEach {
            when (it) {
                newStatus -> delegates[it]?.visibility = View.VISIBLE
                else -> delegates[it]?.visibility = View.GONE
            }
        }
        currStatus = newStatus
    }
}