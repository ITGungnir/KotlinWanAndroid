package app.itgungnir.kwa.common.widget.banner

import android.content.Context
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import app.itgungnir.kwa.common.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.view_banner.view.*
import java.util.concurrent.TimeUnit

class Banner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var disposable: Disposable? = null

    private var manager: LinearLayoutManager

    private var currPage: Int = 1

    private var items: MutableList<Any> = mutableListOf()

    private var onPageChange: ((position: Int) -> Unit)? = null

    private var bannerAdapter: BannerAdapter? = null

    init {
        View.inflate(context, R.layout.view_banner, this)

        recyclerView.apply {
            manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = manager
            PagerSnapHelper().attachToRecyclerView(this)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    when (newState) {
                        RecyclerView.SCROLL_STATE_IDLE -> {
                            val currIndex = manager.findFirstCompletelyVisibleItemPosition()
                            currPage = when {
                                currIndex > items.size - 2 -> 1
                                currIndex < 0 -> currPage
                                currIndex < 1 -> items.size - 2
                                else -> currIndex
                            }
                            if (currPage == items.size - 2 && currIndex == 0) {
                                recyclerView.scrollToPosition(items.size - 2)
                            } else if (currPage == 1 && currIndex == items.size - 1) {
                                recyclerView.scrollToPosition(1)
                            } else {
                                recyclerView.smoothScrollToPosition(currPage)
                            }
                            initDisposable()
                            invokePageChangeListener()
                        }
                        else -> recycleDisposable()
                    }
                }
            })
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        recycleDisposable()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        when (visibility) {
            View.VISIBLE -> initDisposable()
            View.GONE, View.INVISIBLE -> recycleDisposable()
        }
    }

    private fun initDisposable() {
        recycleDisposable()
        this.disposable = Observable.timer(5L, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                recyclerView.smoothScrollToPosition(++currPage)
            }
    }

    private fun recycleDisposable() {
        if (disposable?.isDisposed == false) {
            this.disposable?.dispose()
        }
    }

    fun bind(
        layoutId: Int,
        items: List<Any>,
        render: (position: Int, view: View) -> Unit,
        onClick: (position: Int) -> Unit,
        onPageChange: (position: Int) -> Unit
    ) {
        this.onPageChange = onPageChange
        this.bannerAdapter = BannerAdapter(layoutId = layoutId, render = render, onClick = onClick)
        recyclerView.adapter = bannerAdapter
        update(items)
    }

    fun update(items: List<Any>) {
        if (items.isEmpty()) {
            return
        }
        this.items.clear()
        this.items.add(items[items.size - 1])
        this.items.addAll(items)
        this.items.add(items[0])
        this.bannerAdapter?.update(this.items)
        Handler().post {
            if (currPage >= items.size) {
                currPage = 1
            }
            recyclerView.scrollToPosition(currPage)
            initDisposable()
            invokePageChangeListener()
        }
    }

    private fun invokePageChangeListener() {
        val realPosition = when (currPage) {
            0 -> items.size - 3
            items.size - 1 -> 0
            else -> currPage - 1
        }
        this.onPageChange?.invoke(realPosition)
    }
}