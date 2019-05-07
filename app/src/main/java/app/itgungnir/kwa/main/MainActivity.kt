package app.itgungnir.kwa.main

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.MainActivity
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.common.widget.icon_font.IconFontView
import app.itgungnir.kwa.home.HomeFragment
import app.itgungnir.kwa.mine.MineFragment
import app.itgungnir.kwa.project.ProjectFragment
import app.itgungnir.kwa.tree.TreeFragment
import app.itgungnir.kwa.weixin.WeixinFragment
import kotlinx.android.synthetic.main.activity_main.*
import my.itgungnir.apt.router.annotation.Route
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

        val selectedColor = Color.parseColor("#FF707070")
        val unSelectedColor = Color.parseColor("#FFC2C2C2")

        bottomTab.init(
            targetFrameId = R.id.fragment,
            fragmentManager = supportFragmentManager,
            items = listOf(
                TabItem("首页", "\ue703", "\ue702") to HomeFragment(),
                TabItem("知识体系", "\ue6ef", "\ue6ee") to TreeFragment(),
                TabItem("公众号", "\ue82c", "\ue608") to WeixinFragment(),
                TabItem("项目", "\ue6ec", "\ue6eb") to ProjectFragment(),
                TabItem("我的", "\ue716", "\ue715") to MineFragment()
            ),
            itemLayoutId = R.layout.listitem_bottom_tab,
            render = { view, data, selected ->
                val icon = view.findViewById<IconFontView>(R.id.icon)
                val title = view.findViewById<TextView>(R.id.title)
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
