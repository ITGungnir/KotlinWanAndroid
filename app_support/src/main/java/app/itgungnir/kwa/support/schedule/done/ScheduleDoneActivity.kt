package app.itgungnir.kwa.support.schedule.done

import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import app.itgungnir.kwa.common.ScheduleDoneActivity
import app.itgungnir.kwa.common.color
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.support.R
import app.itgungnir.kwa.support.schedule.ScheduleDelegate
import kotlinx.android.synthetic.main.activity_schedule_done.*
import my.itgungnir.grouter.annotation.Route
import my.itgungnir.rxmvvm.core.mvvm.BaseActivity
import my.itgungnir.rxmvvm.core.mvvm.buildActivityViewModel
import my.itgungnir.ui.dialog.SimpleDialog
import my.itgungnir.ui.easy_adapter.EasyAdapter
import my.itgungnir.ui.easy_adapter.bind
import my.itgungnir.ui.list_footer.ListFooter
import my.itgungnir.ui.status_view.StatusView

@Route(ScheduleDoneActivity)
class ScheduleDoneActivity : BaseActivity() {

    private val viewModel by lazy {
        buildActivityViewModel(
            activity = this,
            viewModelClass = ScheduleDoneViewModel::class.java
        )
    }

    private var listAdapter: EasyAdapter? = null

    private var footer: ListFooter? = null

    override fun layoutId(): Int = R.layout.activity_schedule_done

    override fun initComponent() {

        headBar.title("已完成日程")
            .back(getString(R.string.icon_back)) { finish() }

        scheduleDonePage.apply {
            // Refresh Layout
            refreshLayout().setOnRefreshListener {
                viewModel.getScheduleList()
            }
            // Status View
            statusView().addDelegate(StatusView.Status.SUCCEED, R.layout.view_status_list) {
                val list = it.findViewById<RecyclerView>(R.id.list)
                // Easy Adapter
                listAdapter = list.bind(delegate = ScheduleDelegate(
                    clickCallback = { _, _ -> },
                    longClickCallback = { position, id ->
                        SimpleDialog(
                            bgColor = context.color(R.color.clr_dialog),
                            msgColor = context.color(R.color.text_color_level_2),
                            dividerColor = context.color(R.color.clr_divider),
                            btnColor = context.color(R.color.text_color_level_1),
                            msg = "确定要删除该日程吗？",
                            onConfirm = {
                                viewModel.deleteSchedule(position, id)
                            }
                        ).show(supportFragmentManager, SimpleDialog::class.java.name)
                    }
                ))
                // List Footer
                footer = ListFooter.Builder()
                    .bindTo(list)
                    .render(context.color(R.color.clr_divider), context.color(R.color.clr_background))
                    .doOnLoadMore {
                        if (!refreshLayout().isRefreshing) {
                            viewModel.loadMoreScheduleList()
                        }
                    }
                    .build()
            }.addDelegate(StatusView.Status.EMPTY, R.layout.view_status_list_empty) {
                it.findViewById<TextView>(R.id.tip).text = "还没有完成任何日程~"
            }
        }

        viewModel.getScheduleList()
    }

    override fun observeVM() {

        viewModel.pick(ScheduleDoneState::refreshing)
            .observe(this, Observer { refreshing ->
                refreshing?.a?.let {
                    scheduleDonePage.refreshLayout().isRefreshing = it
                }
            })

        viewModel.pick(ScheduleDoneState::items, ScheduleDoneState::hasMore)
            .observe(this, Observer { states ->
                states?.let {
                    when (it.a.isNotEmpty()) {
                        true -> scheduleDonePage.statusView().succeed { listAdapter?.update(states.a) }
                        else -> scheduleDonePage.statusView().empty { }
                    }
                    footer?.onLoadSucceed(it.b)
                }
            })

        viewModel.pick(ScheduleDoneState::loading)
            .observe(this, Observer { loading ->
                if (loading?.a == true) {
                    footer?.onLoading()
                }
            })

        viewModel.pick(ScheduleDoneState::error)
            .observe(this, Observer { error ->
                error?.a?.message?.let {
                    popToast(it)
                    footer?.onLoadFailed()
                }
            })
    }
}