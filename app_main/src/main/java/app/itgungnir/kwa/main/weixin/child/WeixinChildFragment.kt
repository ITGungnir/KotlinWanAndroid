package app.itgungnir.kwa.main.weixin.child

import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import app.itgungnir.kwa.common.color
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.main.R
import app.itgungnir.kwa.main.weixin.WeixinState
import app.itgungnir.kwa.main.weixin.WeixinViewModel
import kotlinx.android.synthetic.main.fragment_weixin_child.*
import my.itgungnir.rxmvvm.core.mvvm.LazyFragment
import my.itgungnir.rxmvvm.core.mvvm.buildFragmentViewModel
import my.itgungnir.ui.easy_adapter.Differ
import my.itgungnir.ui.easy_adapter.EasyAdapter
import my.itgungnir.ui.easy_adapter.ListItem
import my.itgungnir.ui.easy_adapter.bind
import my.itgungnir.ui.list_footer.ListFooter
import my.itgungnir.ui.status_view.StatusView

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

    private var k: String = ""

    private val handler = Handler()

    companion object {
        fun newInstance(flag: Int) = WeixinChildFragment().apply {
            arguments = Bundle().apply { putInt("FLAG", flag) }
        }
    }

    override fun layoutId(): Int = R.layout.fragment_weixin_child

    override fun initComponent() {
        weixinPage.apply {
            // Refresh Layout
            refreshLayout().setOnRefreshListener {
                flag()?.let { selfViewModel.getArticles(it, k) }
            }
            // Status View
            statusView().addDelegate(StatusView.Status.SUCCEED, R.layout.view_status_list) {
                val list = it.findViewById<RecyclerView>(R.id.list)
                // Recycler View
                listAdapter = list.bind(
                    delegate = WeixinChildDelegate(),
                    diffAnalyzer = object : Differ {
                        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                            oldItem as WeixinChildState.WeixinArticleVO
                            newItem as WeixinChildState.WeixinArticleVO
                            return oldItem.id == newItem.id && oldItem.originId == newItem.originId
                        }

                        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                            oldItem as WeixinChildState.WeixinArticleVO
                            newItem as WeixinChildState.WeixinArticleVO
                            return oldItem.date == newItem.date
                        }

                        override fun getChangePayload(oldItem: ListItem, newItem: ListItem): Bundle? {
                            oldItem as WeixinChildState.WeixinArticleVO
                            newItem as WeixinChildState.WeixinArticleVO
                            val bundle = Bundle()
                            if (oldItem.date != newItem.date) {
                                bundle.putString("PL_DATE", newItem.date)
                            }
                            return if (bundle.isEmpty) null else bundle
                        }
                    })
                // Footer
                footer = ListFooter.Builder()
                    .bindTo(list)
                    .render(context.color(R.color.clr_divider), context.color(R.color.clr_background))
                    .doOnLoadMore {
                        if (!refreshLayout().isRefreshing) {
                            flag()?.let { flag -> selfViewModel.loadMoreArticles(flag, k) }
                        }
                    }
                    .build()
            }.addDelegate(StatusView.Status.EMPTY, R.layout.view_status_list_empty) {
                it.findViewById<TextView>(R.id.tip).text = "本公众号暂无文章~"
            }
        }
    }

    override fun onLazyLoad() {
        flag()?.let { selfViewModel.getArticles(it, k) }
    }

    override fun observeVM() {

        parentViewModel.pick(WeixinState::currTab, WeixinState::k)
            .observe(this, Observer { data ->
                data?.let {
                    if (it.a?.id == flag() && it.b != k) {
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
                    popToast(it)
                    footer?.onLoadFailed()
                }
            })
    }

    private fun flag() = arguments?.getInt("FLAG")
}