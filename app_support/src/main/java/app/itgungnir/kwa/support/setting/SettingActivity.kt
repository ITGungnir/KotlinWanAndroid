package app.itgungnir.kwa.support.setting

import android.os.Bundle
import androidx.lifecycle.Observer
import app.itgungnir.kwa.common.ICON_BACK
import app.itgungnir.kwa.common.SettingActivity
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.support.R
import app.itgungnir.kwa.support.setting.delegate.*
import kotlinx.android.synthetic.main.activity_setting.*
import my.itgungnir.grouter.annotation.Route
import my.itgungnir.rxmvvm.core.mvvm.BaseActivity
import my.itgungnir.rxmvvm.core.mvvm.buildActivityViewModel
import my.itgungnir.ui.easy_adapter.Differ
import my.itgungnir.ui.easy_adapter.EasyAdapter
import my.itgungnir.ui.easy_adapter.ListItem
import my.itgungnir.ui.easy_adapter.bind
import org.jetbrains.anko.email

@Route(SettingActivity)
class SettingActivity : BaseActivity() {

    private var listAdapter: EasyAdapter? = null

    private val viewModel by lazy {
        buildActivityViewModel(
            activity = this,
            viewModelClass = SettingViewModel::class.java
        )
    }

    override fun layoutId(): Int = R.layout.activity_setting

    override fun initComponent() {

        headBar.title("设置")
            .back(ICON_BACK) { finish() }

        listAdapter = list.bind(diffAnalyzer = object : Differ {
            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean = false
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean = false
            override fun getChangePayload(oldItem: ListItem, newItem: ListItem): Bundle? = null
        }).map({ data -> data is SettingState.DividerVO }, DividerDelegate())
            .map({ data -> data is SettingState.CheckableVO }, CheckableDelegate(checkCallback = { id ->
                when (id) {
                    1 -> popToast("正在开发中，敬请期待~") // TODO 自动缓存
                    2 -> popToast("正在开发中，敬请期待~") // TODO 无图模式
                    3 -> popToast("正在开发中，敬请期待~") // TODO 夜间模式
                }
            }))
            .map({ data -> data is SettingState.DigitalVO }, DigitalDelegate(digitalClickCallback = { id ->
                when (id) {
                    4 -> popToast("正在开发中，敬请期待~") // TODO 清除缓存
                }
            }))
            .map({ data -> data is SettingState.NavigableVO }, NavigableDelegate(navigateCallback = { id ->
                when (id) {
                    5 -> when (email(email = "itgungnir@163.com")) {
                        true -> popToast("反馈成功！")
                        else -> popToast("发送邮件失败，请检查邮箱设置")
                    }
                    6 -> AboutUsDialog().show(supportFragmentManager, AboutUsDialog::class.java.name)
                }
            }))
            .map({ data -> data is SettingState.ButtonVO }, ButtonDelegate(callback = {
                finish()
            }))

        viewModel.getSettingList()
    }

    override fun observeVM() {

        viewModel.pick(SettingState::items)
            .observe(this, Observer { items ->
                items?.a?.let {
                    listAdapter?.update(it)
                }
            })

        viewModel.pick(SettingState::error)
            .observe(this, Observer { error ->
                error?.a?.message?.let {
                    popToast(it)
                }
            })
    }
}