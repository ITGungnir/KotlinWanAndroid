package app.itgungnir.kwa.common.widget.banner

import android.annotation.SuppressLint
import android.content.Context
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

    private var items: MutableList<BannerItem> = mutableListOf()

    init {
        apply {
            View.inflate(context, R.layout.view_banner, this)
        }
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
                            renderBottomBar()
                        }
                        else -> recycleDisposable()
                    }
                }
            })
        }
    }

    fun setAdapter(items: List<BannerItem>, onItemClickBlock: (String, String) -> Unit) {
        this.items.clear()
        this.items.add(items[items.size - 1])
        this.items.addAll(items)
        this.items.add(items[0])
        recyclerView.adapter = BannerAdapter(this.items).apply {
            listener = onItemClickBlock
        }
        recyclerView.scrollToPosition(currPage)
        initDisposable()
        renderBottomBar()
    }

    @SuppressLint("SetTextI18n")
    private fun renderBottomBar() {
        title.text = this.items[currPage].title
        index.text = "$currPage/${this.items.size - 2}"
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
}