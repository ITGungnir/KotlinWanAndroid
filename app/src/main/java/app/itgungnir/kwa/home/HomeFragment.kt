package app.itgungnir.kwa.home

import android.arch.lifecycle.Observer
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.ext.bind
import app.itgungnir.kwa.common.widget.recycler_footer.FooterStatus
import app.itgungnir.kwa.common.widget.recycler_footer.RecyclerFooter
import app.itgungnir.kwa.common.widget.recycler_list.RecyclerListAdapter
import app.itgungnir.kwa.common.widget.recycler_list.RecyclerListStatus
import app.itgungnir.kwa.home.delegate.BannerDelegate
import app.itgungnir.kwa.home.delegate.BannerVO
import app.itgungnir.kwa.home.delegate.HomeArticleDelegate
import app.itgungnir.kwa.home.delegate.HomeArticleVO
import kotlinx.android.synthetic.main.layout_common_page.*
import my.itgungnir.rxmvvm.core.ext.createVM
import my.itgungnir.rxmvvm.core.mvvm.BaseFragment
import org.jetbrains.anko.support.v4.toast

class HomeFragment : BaseFragment<HomeVM>() {

    private var listAdapter: RecyclerListAdapter? = null

    private var footer: RecyclerFooter? = null

    override fun contentView(): Int = R.layout.layout_common_page

    override fun shouldBindLifecycle(): Boolean = true

    override fun obtainVM(): HomeVM = createVM()

    override fun initComponent() {

        // Title Bar
        titleBar.title("首页")
            .addToolButton("\ue833") {
                toast("scan")
            }

        // Swipe Refresh Layout
        refreshLayout.setOnRefreshListener {
            vm?.getHomeData()
        }

        // Recycler View
        listAdapter = list.bind()
            .map({ data -> data is BannerVO }, BannerDelegate())
            .map({ data -> data is HomeArticleVO }, HomeArticleDelegate())

        // Recycler Footer
        footer = RecyclerFooter.Builder()
            .bindTo(list)
            .doOnLoadMore {
                if (!refreshLayout.isRefreshing) {
                    vm?.loadMoreHomeData()
                }
            }.build()

        fab.setOnClickListener {
            list.smoothScrollToPosition(0)
        }

        // Init Data
        vm?.getHomeData()
    }

    override fun observeVM() {
        vm?.listDataState?.observe(this, Observer {
            when (it?.status) {
                RecyclerListStatus.Status.PROCESSING -> {
                    refreshLayout.isRefreshing = true
                }
                RecyclerListStatus.Status.SUCCESS -> {
                    listAdapter?.update(it.data!!)
                }
                RecyclerListStatus.Status.COMPLETE -> {
                    refreshLayout.isRefreshing = false
                    list.scrollToPosition(0)
                }
                RecyclerListStatus.Status.ERROR -> {
                    dispatchError(it.error!!)
                }
            }
        })
        vm?.footerState?.observe(this, Observer {
            when (it?.status) {
                FooterStatus.Status.PROGRESSING -> footer?.onLoading()
                FooterStatus.Status.SUCCEED -> footer?.onLoadSucceed(true)
                FooterStatus.Status.NO_MORE -> footer?.onLoadSucceed(false)
                FooterStatus.Status.FAILED -> footer?.onLoadFailed()
            }
        })
    }
}