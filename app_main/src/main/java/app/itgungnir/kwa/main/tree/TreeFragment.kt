package app.itgungnir.kwa.main.tree

import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.main.R
import app.itgungnir.kwa.main.tree.navigation.NavigationDialog
import app.itgungnir.kwa.main.tree.tools.ToolsDialog
import kotlinx.android.synthetic.main.fragment_tree.*
import my.itgungnir.rxmvvm.core.mvvm.BaseFragment
import my.itgungnir.rxmvvm.core.mvvm.buildActivityViewModel
import my.itgungnir.ui.dp2px
import my.itgungnir.ui.easy_adapter.EasyAdapter
import my.itgungnir.ui.easy_adapter.bind
import my.itgungnir.ui.status_view.StatusView
import org.jetbrains.anko.bottomPadding

class TreeFragment : BaseFragment() {

    private val viewModel by lazy {
        buildActivityViewModel(
            activity = activity!!,
            viewModelClass = TreeViewModel::class.java
        )
    }

    private var listAdapter: EasyAdapter? = null

    override fun layoutId(): Int = R.layout.fragment_tree

    override fun initComponent() {
        // Head Bar
        headBar.title("知识体系")
            .addToolButton(getString(R.string.icon_tools)) {
                ToolsDialog().show(childFragmentManager, ToolsDialog::class.java.name)
            }
            .addToolButton(getString(R.string.icon_navigation)) {
                NavigationDialog().show(childFragmentManager, NavigationDialog::class.java.name)
            }
        // Common Page
        treePage.apply {
            // Swipe Refresh Layout
            refreshLayout().setOnRefreshListener {
                viewModel.getTreeList()
            }
            // Status View
            statusView().addDelegate(StatusView.Status.SUCCEED, R.layout.view_status_list) {
                it.findViewById<RecyclerView>(R.id.list).apply {
                    bottomPadding = context.dp2px(10.0F).toInt()
                    listAdapter = this.bind()
                        .addDelegate({ true }, TreeDelegate())
                        .initialize()
                }
            }.addDelegate(StatusView.Status.EMPTY, R.layout.view_status_list_empty) {
                it.findViewById<TextView>(R.id.tip).text = "还没有知识体系~"
            }
        }
        // Init Data
        viewModel.getTreeList()
    }

    override fun observeVM() {

        viewModel.pick(TreeState::refreshing)
            .observe(this, Observer { refreshing ->
                refreshing?.a?.let {
                    treePage.refreshLayout().isRefreshing = it
                }
            })

        viewModel.pick(TreeState::items)
            .observe(this, Observer { items ->
                items?.a?.let { data ->
                    treePage.statusView().succeed { listAdapter?.update(data) }
                }
            })

        viewModel.pick(TreeState::error)
            .observe(this, Observer { error ->
                error?.a?.message?.let {
                    popToast(it)
                    treePage.statusView().empty { }
                }
            })
    }
}