package app.itgungnir.kwa.home

import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.widget.recycler_footer.RecyclerFooter
import app.itgungnir.kwa.common.widget.recycler_list.RecyclerListAdapter
import app.itgungnir.kwa.common.widget.recycler_list.bind
import app.itgungnir.kwa.common.widget.status_view.StatusView
import app.itgungnir.kwa.home.delegate.BannerDelegate
import app.itgungnir.kwa.home.delegate.HomeArticleDelegate
import kotlinx.android.synthetic.main.fragment_home.*
import my.itgungnir.rxmvvm.core.mvvm.BaseFragment
import my.itgungnir.rxmvvm.core.mvvm.buildFragmentViewModel
import org.jetbrains.anko.support.v4.toast

class HomeFragment : BaseFragment() {

    private val viewModel by lazy {
        buildFragmentViewModel(
            fragment = this,
            viewModelClass = HomeViewModel::class.java
        )
    }

    override fun layoutId(): Int = R.layout.fragment_home

    private var listAdapter: RecyclerListAdapter? = null

    private var footer: RecyclerFooter? = null

    override fun initComponent() {
        recyclerPage.apply {
            // Title Bar
            titleBar().title("首页")
                .addToolButton("\ue833") {
                    // TODO
                }
            // Swipe Refresh Layout
            refreshLayout().setOnRefreshListener {
                viewModel.getHomeData()
            }
            // Status View
            statusView().addDelegate(StatusView.Status.SUCCEED, R.layout.delegate_list) {
                val list = it.findViewById<RecyclerView>(R.id.list)
                // Recycler View
                listAdapter = list.bind()
                    .map({ data -> data is HomeState.BannerVO }, BannerDelegate())
                    .map({ data -> data is HomeState.ArticleVO }, HomeArticleDelegate())
                // Recycler Footer
                footer = RecyclerFooter.Builder()
                    .bindTo(list)
                    .doOnLoadMore {
                        if (!recyclerPage.refreshLayout().isRefreshing) {
                            viewModel.loadMoreHomeData()
                        }
                    }.build()
            }.addDelegate(StatusView.Status.EMPTY, R.layout.delegate_empty) {
                it.findViewById<TextView>(R.id.tip).text = "暂时没有文章~"
            }
        }
        // Init Data
        viewModel.getHomeData()
    }

    override fun observeVM() {

        viewModel.pick(HomeState::refreshing)
            .observe(this, Observer { refreshing ->
                refreshing?.a?.let {
                    recyclerPage.refreshLayout().isRefreshing = it
                }
            })

        viewModel.pick(HomeState::dataList, HomeState::hasMore)
            .observe(this, Observer { states ->
                states?.let {
                    when (it.a.isNotEmpty()) {
                        true -> recyclerPage.statusView().succeed { listAdapter?.update(states.a) }
                        else -> recyclerPage.statusView().empty { }
                    }
                    footer?.onLoadSucceed(it.b)
                }
            })

        viewModel.pick(HomeState::loading)
            .observe(this, Observer { loading ->
                if (loading?.a == true) {
                    footer?.onLoading()
                }
            })

        viewModel.pick(HomeState::error)
            .observe(this, Observer { error ->
                error?.a?.message?.let {
                    toast(it)
                    footer?.onLoadFailed()
                }
            })
    }
}