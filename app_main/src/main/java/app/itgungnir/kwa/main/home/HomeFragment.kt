package app.itgungnir.kwa.main.home

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import app.itgungnir.kwa.common.ICON_SEARCH
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.main.R
import app.itgungnir.kwa.main.home.delegate.BannerDelegate
import app.itgungnir.kwa.main.home.delegate.HomeArticleDelegate
import app.itgungnir.kwa.main.home.search.SearchDialog
import kotlinx.android.synthetic.main.fragment_home.*
import my.itgungnir.rxmvvm.core.mvvm.BaseFragment
import my.itgungnir.rxmvvm.core.mvvm.buildFragmentViewModel
import my.itgungnir.ui.easy_adapter.Differ
import my.itgungnir.ui.easy_adapter.EasyAdapter
import my.itgungnir.ui.easy_adapter.ListItem
import my.itgungnir.ui.easy_adapter.bind
import my.itgungnir.ui.list_footer.ListFooter
import my.itgungnir.ui.status_view.StatusView

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
            .addToolButton(ICON_SEARCH) {
                SearchDialog().show(childFragmentManager, SearchDialog::class.java.name)
            }
        // Common Page
        homePage.apply {
            // Swipe Refresh Layout
            refreshLayout().setOnRefreshListener {
                viewModel.getHomeData()
            }
            // Status View
            statusView().addDelegate(StatusView.Status.SUCCEED, R.layout.view_status_list) {
                val list = it.findViewById<RecyclerView>(R.id.list)
                // Recycler View
                listAdapter = list.bind(diffAnalyzer = object : Differ {
                    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                        if (oldItem is HomeState.HomeArticleVO && newItem is HomeState.HomeArticleVO) {
                            oldItem.id == newItem.id && oldItem.originId == newItem.originId
                        } else {
                            false
                        }

                    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                        if (oldItem is HomeState.HomeArticleVO && newItem is HomeState.HomeArticleVO) {
                            oldItem.date == newItem.date
                        } else {
                            false
                        }

                    override fun getChangePayload(oldItem: ListItem, newItem: ListItem): Bundle? =
                        if (oldItem is HomeState.HomeArticleVO && newItem is HomeState.HomeArticleVO) {
                            val bundle = Bundle()
                            if (oldItem.date != newItem.date) {
                                bundle.putString("PL_DATE", newItem.date)
                            }
                            if (bundle.isEmpty) null else bundle
                        } else {
                            null
                        }
                }).map({ data -> data is HomeState.BannerVO }, BannerDelegate())
                    .map({ data -> data is HomeState.HomeArticleVO }, HomeArticleDelegate())
                // Recycler Footer
                footer = ListFooter.Builder()
                    .bindTo(list)
                    .doOnLoadMore {
                        if (!homePage.refreshLayout().isRefreshing) {
                            viewModel.loadMoreHomeData()
                        }
                    }.build()
            }.addDelegate(StatusView.Status.EMPTY, R.layout.view_status_list_empty) {
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