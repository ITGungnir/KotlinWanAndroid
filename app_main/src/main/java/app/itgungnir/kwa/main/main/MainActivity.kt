package app.itgungnir.kwa.main.main

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import app.itgungnir.kwa.common.MainActivity
import app.itgungnir.kwa.common.color
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.main.R
import app.itgungnir.kwa.main.home.HomeFragment
import app.itgungnir.kwa.main.mine.MineFragment
import app.itgungnir.kwa.main.project.ProjectFragment
import app.itgungnir.kwa.main.tree.TreeFragment
import app.itgungnir.kwa.main.weixin.WeixinFragment
import kotlinx.android.synthetic.main.activity_main.*
import my.itgungnir.grouter.annotation.Route
import my.itgungnir.ui.icon_font.IconFontView
import org.jetbrains.anko.textColor
import org.joda.time.DateTime

@Route(MainActivity)
class MainActivity : AppCompatActivity() {

    private var lastTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent()
    }

    private fun initComponent() {

        val selectedColor = this.color(R.color.clr_icon_selected)
        val unSelectedColor = this.color(R.color.clr_icon_unselected)

        bottomTab.init(
            targetFrameId = R.id.fragment,
            fragmentManager = supportFragmentManager,
            items = listOf(
                TabItem(
                    "首页",
                    getString(R.string.icon_home_normal),
                    getString(R.string.icon_home_select)
                ) to HomeFragment(),
                TabItem(
                    "知识体系",
                    getString(R.string.icon_tree_normal),
                    getString(R.string.icon_tree_select)
                ) to TreeFragment(),
                TabItem(
                    "公众号",
                    getString(R.string.icon_weixin_normal),
                    getString(R.string.icon_weixin_select)
                ) to WeixinFragment(),
                TabItem(
                    "项目",
                    getString(R.string.icon_project_normal),
                    getString(R.string.icon_project_select)
                ) to ProjectFragment(),
                TabItem(
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
