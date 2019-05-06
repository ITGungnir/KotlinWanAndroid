package app.itgungnir.kwa.weixin.child

import android.os.Handler
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.common.widget.easy_adapter.bind
import app.itgungnir.kwa.common.widget.list_footer.ListFooter
import app.itgungnir.kwa.common.widget.status_view.StatusView
import app.itgungnir.kwa.weixin.WeixinState
import app.itgungnir.kwa.weixin.WeixinViewModel
import kotlinx.android.synthetic.main.fragment_weixin_child.*
import my.itgungnir.rxmvvm.core.mvvm.LazyFragment
import my.itgungnir.rxmvvm.core.mvvm.buildFragmentViewModel
import org.jetbrains.anko.support.v4.toast

class WeixinChildFragment : LazyFragment() {

    private var listAdapter: EasyAdapter? = null

    private var footer: ListFooter? = null

    private val parentViewModel by lazy {
        buildFragmentViewModel(
            fragment = parentFragment!!,
            viewModelClass = WeixinViewModel::class.java
        )
    }

    private val selfViewModel by lazy {
        buildFragmentViewModel(
            fragment = this,
            viewModelClass = WeixinChildViewModel::class.java
        )
    }

    private var flag: Int = -1

    private var k: String = ""

    private val handler = Handler()

    companion object {
        fun newInstance(flag: Int) = WeixinChildFragment().apply { this.flag = flag }
    }

    override fun layoutId(): Int = R.layout.fragment_weixin_child

    override fun initComponent() {
        weixinPage.apply {
            // Refresh Layout
            refreshLayout().setOnRefreshListener {
                selfViewModel.getArticles(flag, k)
            }
            // Status View
            statusView().addDelegate(StatusView.Status.SUCCEED, R.layout.view_list) {
                val list = it.findViewById<RecyclerView>(R.id.list)
                // Recycler View
                listAdapter = list.bind(delegate = WeixinChildDelegate())
                // Footer
                footer = ListFooter.Builder()
                    .bindTo(list)
                    .doOnLoadMore {
                        if (!refreshLayout().isRefreshing) {
                            selfViewModel.loadMoreArticles(flag, k)
                        }
                    }
                    .build()
            }.addDelegate(StatusView.Status.EMPTY, R.layout.view_empty) {
                it.findViewById<TextView>(R.id.tip).text = "本公众号暂无文章~"
            }
        }
    }

    override fun onLazyLoad() {
        selfViewModel.getArticles(flag, k)
    }

    override fun observeVM() {

        parentViewModel.pick(WeixinState::currTab, WeixinState::k)
            .observe(this, Observer { data ->
                data?.let {
                    if (it.a?.id == flag && it.b != k) {
                        k = it.b
                        selfViewModel.getArticles(it.a!!.id, k)
                    }
                }
            })

        selfViewModel.pick(WeixinChildState::refreshing)
            .observe(this, Observer { refreshing ->
                refreshing?.a?.let {
                    weixinPage.refreshLayout().isRefreshing = it
                }
            })

        selfViewModel.pick(WeixinChildState::items, WeixinChildState::hasMore, WeixinChildState::shouldScrollToTop)
            .observe(this, Observer { states ->
                states?.let {
                    when (it.a.isNotEmpty()) {
                        true -> weixinPage.statusView().succeed { listAdapter?.update(states.a) }
                        else -> weixinPage.statusView().empty { }
                    }
                    footer?.onLoadSucceed(it.b)
                    if (it.c) {
                        handler.post {
                            (weixinPage.statusView().getDelegate(StatusView.Status.SUCCEED) as RecyclerView)
                                .scrollToPosition(0)
                        }
                    }
                }
            })

        selfViewModel.pick(WeixinChildState::loading)
            .observe(this, Observer { loading ->
                if (loading?.a == true) {
                    footer?.onLoading()
                }
            })

        selfViewModel.pick(WeixinChildState::error)
            .observe(this, Observer { error ->
                error?.a?.message?.let {
                    toast(it)
                    footer?.onLoadFailed()
                }
            })
    }
}