package app.itgungnir.kwa.main.main

import android.widget.TextView
import androidx.lifecycle.Observer
import app.itgungnir.kwa.common.MainActivity
import app.itgungnir.kwa.common.color
import app.itgungnir.kwa.common.http.HttpUtil
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.common.redux.AppRedux
import app.itgungnir.kwa.common.redux.AppState
import app.itgungnir.kwa.common.simpleDialog
import app.itgungnir.kwa.main.R
import app.itgungnir.kwa.main.home.HomeFragment
import app.itgungnir.kwa.main.mine.MineFragment
import app.itgungnir.kwa.main.project.ProjectFragment
import app.itgungnir.kwa.main.tree.TreeFragment
import app.itgungnir.kwa.main.weixin.WeixinFragment
import kotlinx.android.synthetic.main.activity_main.*
import my.itgungnir.grouter.annotation.Route
import my.itgungnir.rxmvvm.core.mvvm.BaseActivity
import my.itgungnir.rxmvvm.core.mvvm.buildActivityViewModel
import my.itgungnir.ui.icon_font.IconFontView
import org.jetbrains.anko.textColor
import org.joda.time.DateTime

@Route(MainActivity)
class MainActivity : BaseActivity() {

    private var lastTime = 0L

    private var isDarkMode = AppRedux.instance.isDarkMode()

    private val viewModel by lazy {
        buildActivityViewModel(
            activity = this,
            viewModelClass = MainViewModel::class.java
        )
    }

    override fun layoutId(): Int = R.layout.activity_main

    override fun initComponent() {

        viewModel.getLatestVersion()

        val selectedColor = this.color(R.color.clr_icon_selected)
        val unSelectedColor = this.color(R.color.clr_icon_unselected)

        bottomTab.init(
            targetFrameId = R.id.fragment,
            fragmentManager = supportFragmentManager,
            items = listOf(
                MainState.TabItem(
                    "首页",
                    getString(R.string.icon_home_normal),
                    getString(R.string.icon_home_select)
                ) to HomeFragment(),
                MainState.TabItem(
                    "知识体系",
                    getString(R.string.icon_tree_normal),
                    getString(R.string.icon_tree_select)
                ) to TreeFragment(),
                MainState.TabItem(
                    "公众号",
                    getString(R.string.icon_weixin_normal),
                    getString(R.string.icon_weixin_select)
                ) to WeixinFragment(),
                MainState.TabItem(
                    "项目",
                    getString(R.string.icon_project_normal),
                    getString(R.string.icon_project_select)
                ) to ProjectFragment(),
                MainState.TabItem(
                    "我的",
                    getString(R.string.icon_mine_normal),
                    getString(R.string.icon_mine_select)
                ) to MineFragment()
            ),
            itemLayoutId = R.layout.list_item_main_bottom_tab,
            render = { view, data, selected ->
                val icon = view.findViewById<IconFontView>(R.id.iconView)
                val title = view.findViewById<TextView>(R.id.titleView)
                title.text = data.title
                when (selected) {
                    true -> {
                        icon.text = data.selectedIcon
                        icon.textColor = selectedColor
                        title.textColor = selectedColor
                    }
                    false -> {
                        icon.text = data.unselectedIcon
                        icon.textColor = unSelectedColor
                        title.textColor = unSelectedColor
                    }
                }
            }
        )
    }

    override fun observeVM() {

        AppRedux.instance.pick(AppState::darkMode)
            .observe(this, Observer { darkMode ->
                darkMode?.a?.let {
                    if (it != isDarkMode) {
                        recreate()
                    }
                    isDarkMode = it
                }
            })

        viewModel.pick(MainState::versionInfo)
            .observe(this, Observer { versionInfo ->
                versionInfo?.a?.let {
                    simpleDialog(
                        manager = supportFragmentManager,
                        msg = "发现新版本：\r\nV${it.upgradeVersion}\r\n\r\n${it.upgradeDesc}",
                        onConfirm = { confirmUpdate() }
                    )
                }
            })
    }

    private fun confirmUpdate() {
        if (!HttpUtil.instance.isNetworkConnected(this)) {
            simpleDialog(supportFragmentManager, "当前没有网络！")
            return
        }
        if (!HttpUtil.instance.isWiFiConnected(this)) {
            simpleDialog(supportFragmentManager, "当前处于非WIFI环境，确定要继续下载吗？") {
                startDownloadApk()
            }
            return
        }
        startDownloadApk()
    }

    private fun startDownloadApk() {
        viewModel.withState {
            it.versionInfo?.let { data ->
                val fileName = "KWA_${data.upgradeVersion.replace(".", "_")}.apk"
                HttpUtil.instance.downloadApk(this, data.upgradeUrl, fileName)
            }
        }
    }

    override fun onBackPressed() {
        val currTime = DateTime.now().millis
        if (currTime - lastTime < 1500) {
            this.finish()
        } else {
            this.lastTime = currTime
            popToast("再按一次退出应用")
        }
    }
}
