package app.itgungnir.kwa.main

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.*
import app.itgungnir.kwa.common.widget.icon_font.IconFontView
import app.itgungnir.kwa.home.HomeFragment
import app.itgungnir.kwa.mine.MineFragment
import app.itgungnir.kwa.project.ProjectFragment
import app.itgungnir.kwa.tree.TreeFragment
import app.itgungnir.kwa.weixin.WeixinFragment
import kotlinx.android.synthetic.main.activity_main.*
import my.itgungnir.grouter.annotation.Route
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

        val selectedColor = Color.parseColor(COLOR_ICON_SELECT)
        val unSelectedColor = Color.parseColor(COLOR_ICON_NORMAL)

        bottomTab.init(
            targetFrameId = R.id.fragment,
            fragmentManager = supportFragmentManager,
            items = listOf(
                TabItem("首页", ICON_HOME_NORMAL, ICON_HOME_SELECT) to HomeFragment(),
                TabItem("知识体系", ICON_TREE_NORMAL, ICON_TREE_SELECT) to TreeFragment(),
                TabItem("公众号", ICON_WEIXIN_NORMAL, ICON_WEIXIN_SELECT) to WeixinFragment(),
                TabItem("项目", ICON_PROJECT_NORMAL, ICON_PROJECT_SELECT) to ProjectFragment(),
                TabItem("我的", ICON_MINE_NORMAL, ICON_MINE_SELECT) to MineFragment()
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
