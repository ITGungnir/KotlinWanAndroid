package app.itgungnir.kwa.mine

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.LoginActivity
import app.itgungnir.kwa.common.SettingActivity
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.common.redux.AppRedux
import app.itgungnir.kwa.common.redux.AppState
import app.itgungnir.kwa.common.widget.easy_adapter.Differ
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.common.widget.easy_adapter.ListItem
import app.itgungnir.kwa.common.widget.easy_adapter.bind
import app.itgungnir.kwa.common.widget.input.ProgressButton
import app.itgungnir.kwa.common.widget.list_footer.ListFooter
import app.itgungnir.kwa.common.widget.status_view.StatusView
import app.itgungnir.kwa.mine.add.AddDialog
import app.itgungnir.kwa.mine.delegate.MineDelegate
import kotlinx.android.synthetic.main.fragment_mine.*
import my.itgungnir.apt.router.api.Router
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

        headBar.addToolButton("\ue6df") {
            AddDialog().show(childFragmentManager, AddDialog::class.java.name)
        }.addToolButton("\ue6de") {
            // TODO Schedule
        }.addToolButton("\ue728") {
            Router.instance.with(this)
                .target(SettingActivity)
                .go()
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
                listAdapter = list.bind(
                    delegate = MineDelegate { id, originId -> viewModel.disCollectArticle(id, originId) },
                    diffAnalyzer = object : Differ {
                        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                            (oldItem as MineState.MineDataVO).articleVO.id == (newItem as MineState.MineDataVO).articleVO.id &&
                                    oldItem.articleVO.originId == newItem.articleVO.originId

                        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                            (oldItem as MineState.MineDataVO).articleVO.author == (newItem as MineState.MineDataVO).articleVO.author &&
                                    oldItem.articleVO.title == newItem.articleVO.title &&
                                    oldItem.articleVO.category == newItem.articleVO.category &&
                                    oldItem.articleVO.categoryId == newItem.articleVO.categoryId &&
                                    oldItem.articleVO.date == newItem.articleVO.date &&
                                    oldItem.articleVO.link == newItem.articleVO.link &&
                                    oldItem.deleteVO.id == newItem.deleteVO.id &&
                                    oldItem.deleteVO.originId == newItem.deleteVO.originId

                        override fun getChangePayload(oldItem: ListItem, newItem: ListItem): Bundle? {
                            oldItem as MineState.MineDataVO
                            newItem as MineState.MineDataVO
                            val bundle = Bundle()
                            bundle.putInt("PL_ID", newItem.articleVO.id)
                            bundle.putInt("PL_OID", newItem.articleVO.originId)
                            if (oldItem.articleVO.author != newItem.articleVO.author) {
                                bundle.putString("PL_AUTHOR", newItem.articleVO.author)
                            }
                            if (oldItem.articleVO.title != newItem.articleVO.title) {
                                bundle.putString("PL_TITLE", newItem.articleVO.title)
                            }
                            if (oldItem.articleVO.category != newItem.articleVO.category) {
                                bundle.putString("PL_C", newItem.articleVO.category)
                            }
                            if (oldItem.articleVO.categoryId != newItem.articleVO.categoryId) {
                                bundle.putInt("PL_CID", newItem.articleVO.categoryId)
                            }
                            if (oldItem.articleVO.date != newItem.articleVO.date) {
                                bundle.putString("PL_DATE", newItem.articleVO.date)
                            }
                            if (oldItem.articleVO.link != newItem.articleVO.link) {
                                bundle.putString("PL_LINK", newItem.articleVO.link)
                            }
                            return if (bundle.isEmpty) null else bundle
                        }
                    }
                )
                // List Footer
                footer = ListFooter.Builder()
                    .bindTo(list)
                    .doOnLoadMore {
                        if (!minePage.refreshLayout().isRefreshing) {
                            viewModel.loadMoreMineData()
                        }
                    }
                    .build()
            }.addDelegate(StatusView.Status.EMPTY, R.layout.status_view_list_empty) {
                it.findViewById<TextView>(R.id.tip).text = "收藏列表为空，快去收藏吧~"
            }.addDelegate(StatusView.Status.FAILED, R.layout.status_view_auth) {
                it.findViewById<ProgressButton>(R.id.auth).apply {
                    ready("登录 / 注册")
                    setOnClickListener {
                        Router.instance.with(context)
                            .target(LoginActivity)
                            .go()
                    }
                }
            }
        }
    }

    override fun observeVM() {

        AppRedux.instance.pick(AppState::userName, AppState::collectChanges)
            .observe(this, Observer {
                when (it?.a) {
                    null -> {
                        headBar.title("我的")
                        minePage.statusView().failed { }
                        viewModel.setState { MineState() }
                    }
                    else -> {
                        headBar.title(it.a!!)
                        viewModel.getMineData()
                    }
                }
            })

        viewModel.pick(MineState::refreshing)
            .observe(this, Observer { refreshing ->
                refreshing?.a?.let {
                    minePage.refreshLayout().isRefreshing = it
                }
            })

        viewModel.pick(MineState::items, MineState::hasMore)
            .observe(this, Observer { states ->
                states?.let {
                    if (AppRedux.instance.isUserIn()) {
                        when (it.a.isNotEmpty()) {
                            true -> minePage.statusView().succeed { listAdapter?.update(states.a) }
                            else -> minePage.statusView().empty { }
                        }
                        footer?.onLoadSucceed(it.b ?: false)
                    } else {
                        minePage.statusView().failed { }
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