package app.itgungnir.kwa.project.child

import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.common.widget.easy_adapter.bind
import app.itgungnir.kwa.common.widget.list_footer.ListFooter
import app.itgungnir.kwa.common.widget.status_view.StatusView
import kotlinx.android.synthetic.main.fragment_project_child.*
import my.itgungnir.rxmvvm.core.mvvm.LazyFragment
import my.itgungnir.rxmvvm.core.mvvm.buildFragmentViewModel
import org.jetbrains.anko.support.v4.toast

class ProjectChildFragment : LazyFragment() {

    private var listAdapter: EasyAdapter? = null

    private var footer: ListFooter? = null

    private val viewModel by lazy {
        buildFragmentViewModel(
            fragment = this,
            viewModelClass = ProjectChildViewModel::class.java
        )
    }

    private var flag = 0

    companion object {
        fun newInstance(flag: Int) = ProjectChildFragment().apply { this.flag = flag }
    }

    override fun layoutId(): Int = R.layout.fragment_project_child

    override fun initComponent() {
        projectPage.apply {
            // Refresh Layout
            refreshLayout().setOnRefreshListener {
                viewModel.getProjects(flag)
            }
            // Status View
            statusView().addDelegate(StatusView.Status.SUCCEED, R.layout.status_view_list) {
                val list = it.findViewById<RecyclerView>(R.id.list)
                // Recycler View
                listAdapter = list.bind(delegate = ProjectChildDelegate())
                // Footer
                footer = ListFooter.Builder()
                    .bindTo(list)
                    .doOnLoadMore {
                        if (!refreshLayout().isRefreshing) {
                            viewModel.loadMoreProjects(flag)
                        }
                    }
                    .build()
            }.addDelegate(StatusView.Status.EMPTY, R.layout.status_view_list_empty) {
                it.findViewById<TextView>(R.id.tip).text = "本栏目暂无项目~"
            }
        }
    }

    override fun onLazyLoad() {
        viewModel.getProjects(flag)
    }

    override fun observeVM() {

        viewModel.pick(ProjectChildState::refreshing)
            .observe(this, Observer { refreshing ->
                refreshing?.a?.let {
                    projectPage.refreshLayout().isRefreshing = it
                }
            })

        viewModel.pick(ProjectChildState::items, ProjectChildState::hasMore)
            .observe(this, Observer { states ->
                states?.let {
                    when (it.a.isNotEmpty()) {
                        true -> projectPage.statusView().succeed { listAdapter?.update(states.a) }
                        else -> projectPage.statusView().empty { }
                    }
                    footer?.onLoadSucceed(it.b)
                }
            })

        viewModel.pick(ProjectChildState::loading)
            .observe(this, Observer { loading ->
                if (loading?.a == true) {
                    footer?.onLoading()
                }
            })

        viewModel.pick(ProjectChildState::error)
            .observe(this, Observer { error ->
                error?.a?.message?.let {
                    toast(it)
                    footer?.onLoadFailed()
                }
            })
    }
}