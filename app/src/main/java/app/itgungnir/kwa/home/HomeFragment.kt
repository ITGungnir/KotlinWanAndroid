package app.itgungnir.kwa.home

import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.common.widget.easy_adapter.bind
import app.itgungnir.kwa.common.widget.list_footer.ListFooter
import app.itgungnir.kwa.common.widget.status_view.StatusView
import app.itgungnir.kwa.home.delegate.BannerDelegate
import app.itgungnir.kwa.home.delegate.HomeArticleDelegate
import app.itgungnir.kwa.search.SearchDialog
import kotlinx.android.synthetic.main.fragment_home.*
import my.itgungnir.rxmvvm.core.mvvm.BaseFragment
import my.itgungnir.rxmvvm.core.mvvm.buildFragmentViewModel

class HomeFragment : BaseFragment() {

    private val viewModel by lazy {
        buildFragmentViewModel(
            fragment = this,
            viewModelClass = HomeViewModel::class.java
        )
    }

    override fun layoutId(): Int = R.layout.fragment_home

    private var listAdapter: EasyAdapter? = null

    private var footer: ListFooter? = null

    override fun initComponent() {
        // Head Bar
        headBar.title("首页")
            .addToolButton("\ue833") {
                SearchDialog().show(childFragmentManager, SearchDialog::class.java.name)
            }
        // Common Page
        homePage.apply {
            // Swipe Refresh Layout
            refreshLayout().setOnRefreshListener {
                viewModel.getHomeData()
            }
            // Status View
            statusView().addDelegate(StatusView.Status.SUCCEED, R.layout.status_view_list) {
                val list = it.findViewById<RecyclerView>(R.id.list)
                // Recycler View
                listAdapter = list.bind()
                    .map({ data -> data is HomeState.BannerVO }, BannerDelegate())
                    .map({ data -> data is HomeState.HomeArticleVO }, HomeArticleDelegate())
                // Recycler Footer
                footer = ListFooter.Builder()
                    .bindTo(list)
                    .doOnLoadMore {
                        if (!homePage.refreshLayout().isRefreshing) {
                            viewModel.loadMoreHomeData()
                        }
                    }.build()
            }.addDelegate(StatusView.Status.EMPTY, R.layout.status_view_list_empty) {
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
                    homePage.refreshLayout().isRefreshing = it
                }
            })

        viewModel.pick(HomeState::dataList, HomeState::hasMore)
            .observe(this, Observer { states ->
                states?.let {
                    when (it.a.isNotEmpty()) {
                        true -> homePage.statusView().succeed { listAdapter?.update(states.a) }
                        else -> homePage.statusView().empty { }
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
                    popToast(it)
                    footer?.onLoadFailed()
                }
            })
    }
}