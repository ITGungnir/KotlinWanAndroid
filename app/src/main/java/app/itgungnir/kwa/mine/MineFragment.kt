package app.itgungnir.kwa.mine

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.common.widget.easy_adapter.bind
import app.itgungnir.kwa.common.widget.list_footer.ListFooter
import app.itgungnir.kwa.common.widget.status_view.StatusView
import app.itgungnir.kwa.mine.delegate.MineArticleDelegate
import app.itgungnir.kwa.mine.delegate.MineProfileDelegate
import kotlinx.android.synthetic.main.fragment_mine.*
import my.itgungnir.rxmvvm.core.mvvm.BaseFragment
import my.itgungnir.rxmvvm.core.mvvm.buildFragmentViewModel

class MineFragment : BaseFragment() {

    private var listAdapter: EasyAdapter? = null

    private var footer: ListFooter? = null

    private val viewModel by lazy {
        buildFragmentViewModel(
            fragment = this,
            viewModelClass = MineViewModel::class.java
        )
    }

    override fun layoutId(): Int = R.layout.fragment_mine

    override fun initComponent() {

        headBar.title("我的")
            .addToolButton("\ue6de") {
                // TODO TODO
            }.addToolButton("\ue728") {
                // TODO 设置
            }

        minePage.apply {
            // Refresh Layout
            refreshLayout().setOnRefreshListener {
                viewModel.getMineData()
            }
            // Status View
            statusView().addDelegate(StatusView.Status.SUCCEED, R.layout.status_view_list) {
                val list = it.findViewById<RecyclerView>(R.id.list)
                // Recycler View
                listAdapter = list.bind()
                    .map({ data -> data is MineState.MineProfileVO }, MineProfileDelegate())
                    .map({ data -> data is MineState.MineArticleVO }, MineArticleDelegate())
                // List Footer
                footer = ListFooter.Builder()
                    .bindTo(list)
                    .doOnLoadMore {
                        if (!minePage.refreshLayout().isRefreshing) {
                            viewModel.loadMoreMineData()
                        }
                    }
                    .build()
            }
        }

        // Init data
        viewModel.getMineData()
    }

    override fun observeVM() {

        viewModel.pick(MineState::refreshing)
            .observe(this, Observer { refreshing ->
                refreshing?.a?.let {
                    minePage.refreshLayout().isRefreshing = it
                }
            })

        viewModel.pick(MineState::items, MineState::hasMore)
            .observe(this, Observer { states ->
                states?.let {
                    minePage.statusView().succeed { listAdapter?.update(states.a) }
                    if (states.a.size == 1) {
                        footer?.hide()
                    } else {
                        footer?.onLoadSucceed(it.b)
                    }
                }
            })

        viewModel.pick(MineState::loading)
            .observe(this, Observer { loading ->
                if (loading?.a == true) {
                    footer?.onLoading()
                }
            })

        viewModel.pick(MineState::error)
            .observe(this, Observer { error ->
                error?.a?.message?.let {
                    popToast(it)
                    footer?.onLoadFailed()
                }
            })
    }
}