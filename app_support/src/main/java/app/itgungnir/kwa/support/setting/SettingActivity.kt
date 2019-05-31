package app.itgungnir.kwa.support.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import app.itgungnir.kwa.common.SettingActivity
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.common.redux.*
import app.itgungnir.kwa.common.simpleDialog
import app.itgungnir.kwa.common.util.CacheUtil
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
            .back(getString(R.string.icon_back)) { finish() }

        listAdapter = list.bind(diffAnalyzer = object : Differ {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                if (oldItem is SettingState.CheckableVO && newItem is SettingState.CheckableVO) {
                    oldItem.id == newItem.id
                } else if (oldItem is SettingState.DigitalVO && newItem is SettingState.DigitalVO) {
                    oldItem.id == newItem.id
                } else if (oldItem is SettingState.NavigableVO && newItem is SettingState.NavigableVO) {
                    oldItem.id == newItem.id
                } else {
                    oldItem is SettingState.ButtonVO && newItem is SettingState.ButtonVO ||
                            oldItem is SettingState.DividerVO && newItem is SettingState.DividerVO
                }


            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                if (oldItem is SettingState.CheckableVO && newItem is SettingState.CheckableVO) {
                    oldItem.iconFont == newItem.iconFont && oldItem.title == newItem.title &&
                            oldItem.isChecked == newItem.isChecked
                } else if (oldItem is SettingState.DigitalVO && newItem is SettingState.DigitalVO) {
                    oldItem.iconFont == newItem.iconFont && oldItem.title == newItem.title &&
                            oldItem.digit == newItem.digit
                } else {
                    oldItem is SettingState.ButtonVO && newItem is SettingState.ButtonVO ||
                            oldItem is SettingState.DividerVO && newItem is SettingState.DividerVO ||
                            oldItem is SettingState.NavigableVO && newItem is SettingState.NavigableVO
                }

            override fun getChangePayload(oldItem: ListItem, newItem: ListItem): Bundle? {
                if (oldItem is SettingState.CheckableVO && newItem is SettingState.CheckableVO) {
                    val bundle = Bundle()
                    if (oldItem.isChecked != newItem.isChecked) {
                        bundle.putBoolean("PL_CHECKED", newItem.isChecked)
                    }
                    return if (bundle.isEmpty) null else bundle
                } else if (oldItem is SettingState.DigitalVO && newItem is SettingState.DigitalVO) {
                    val bundle = Bundle()
                    if (oldItem.digit != newItem.digit) {
                        bundle.putString("PL_DIGIT", newItem.digit)
                    }
                    return if (bundle.isEmpty) null else bundle
                }
                return null
            }
        }).addDelegate({ data -> data is SettingState.DividerVO }, DividerDelegate())
            .addDelegate({ data -> data is SettingState.CheckableVO }, CheckableDelegate(checkCallback = { id ->
                when (id) {
                    1 -> {
                        CacheUtil.instance.clearCache()
                        AppRedux.instance.dispatch(ToggleAutoCache)
                    }
                    2 -> AppRedux.instance.dispatch(ToggleNoImage)
                    3 -> {
                        AppRedux.instance.dispatch(ToggleDarkMode)
                        when (AppRedux.instance.isDarkMode()) {
                            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        }
                        recreate()
                    }
                }
            }))
            .addDelegate({ data -> data is SettingState.DigitalVO }, DigitalDelegate(digitalClickCallback = { id ->
                when (id) {
                    4 -> {
                        CacheUtil.instance.clearCache()
                        viewModel.setState {
                            copy(
                                items = items.map { item ->
                                    if (item is SettingState.DigitalVO) {
                                        item.copy(digit = CacheUtil.instance.getCacheSize())
                                    } else {
                                        item
                                    }
                                }
                            )
                        }
                    }
                }
            }))
            .addDelegate({ data -> data is SettingState.NavigableVO }, NavigableDelegate(navigateCallback = { id ->
                when (id) {
                    5 -> email(email = "itgungnir@163.com")
                    6 -> AboutUsDialog().show(supportFragmentManager, AboutUsDialog::class.java.name)
                }
            }))
            .addDelegate({ data -> data is SettingState.ButtonVO }, ButtonDelegate(callback = {
                this.simpleDialog(supportFragmentManager, "确定要退出当前登录吗？") {
                    AppRedux.instance.dispatch(ClearUserInfo)
                    finish()
                }
            }))
            .initialize()

        viewModel.getSettingList(this)
    }

    override fun observeVM() {

        AppRedux.instance.pick(AppState::autoCache, AppState::noImage, AppState::darkMode)
            .observe(this, Observer { states ->
                states?.let {
                    viewModel.setState {
                        copy(
                            items = items.map { item ->
                                if (item is SettingState.CheckableVO) {
                                    item.copy(
                                        isChecked = when (item.id) {
                                            1 -> it.a
                                            2 -> it.b
                                            else -> it.c
                                        }
                                    )
                                } else {
                                    item
                                }
                            }
                        )
                    }
                }
            })

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