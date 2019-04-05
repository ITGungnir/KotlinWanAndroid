package app.itgungnir.kwa.common.widget.recycler_footer

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

@Suppress("JoinDeclarationAndAssignment")
class RecyclerFooter(private val recyclerView: RecyclerView, private val loadMore: () -> Unit) {

    private var listAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    private var footerAdapter: FooterAdapter

    private var lastVisibleItem = 0

    private var hasMore = false

    private constructor(builder: Builder) : this(builder.recyclerView, builder.listener)

    init {
        listAdapter = recyclerView.adapter!!
        footerAdapter =
            FooterAdapter(
                listAdapter,
                FooterStatus.Status.SUCCEED
            )
        listAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() =
                footerAdapter.notifyDataSetChanged()

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) =
                footerAdapter.notifyItemRangeRemoved(positionStart, itemCount)

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) =
                footerAdapter.notifyItemMoved(fromPosition, toPosition)

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) =
                footerAdapter.notifyItemRangeInserted(positionStart, itemCount)

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) =
                footerAdapter.notifyItemRangeChanged(positionStart, itemCount)

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) =
                footerAdapter.notifyItemRangeChanged(positionStart, itemCount, payload)
        })

        applyFooterAdapter()
        applyOnScrollListener()
    }

    private fun applyFooterAdapter() {
        this.recyclerView.adapter = footerAdapter
    }

    private fun applyOnScrollListener() {
        this.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (hasMore && lastVisibleItem == listAdapter.itemCount) {
                        loadMore()
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                when (recyclerView.layoutManager) {
                    is LinearLayoutManager -> lastVisibleItem =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    is GridLayoutManager -> lastVisibleItem =
                        (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                }
                if (dy <= 0) {
                    lastVisibleItem--
                }
            }
        })
    }

    fun onLoading() {
        footerAdapter.notifyStatusChanged(FooterStatus.Status.PROGRESSING)
    }

    fun onLoadSucceed(hasMore: Boolean) {
        val isInit = this.recyclerView.childCount < 2
        this.hasMore = hasMore
        if (hasMore) {
            footerAdapter.notifyStatusChanged(FooterStatus.Status.SUCCEED)
        } else {
            footerAdapter.notifyStatusChanged(FooterStatus.Status.NO_MORE)
        }
        if (isInit) this.recyclerView.scrollToPosition(0)
    }

    fun onLoadFailed() {
        footerAdapter.notifyStatusChanged(FooterStatus.Status.FAILED)
    }

    class Builder {

        lateinit var recyclerView: RecyclerView
            private set

        lateinit var listener: () -> Unit
            private set

        fun bindTo(recyclerView: RecyclerView) = apply {
            this.recyclerView = recyclerView
        }

        fun doOnLoadMore(listener: () -> Unit) = apply {
            this.listener = listener
        }

        fun build() = RecyclerFooter(this)
    }
}