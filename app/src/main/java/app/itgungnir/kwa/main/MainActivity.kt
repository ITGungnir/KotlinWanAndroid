package app.itgungnir.kwa.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.widget.bottom_tab.BottomTab
import app.itgungnir.kwa.common.widget.bottom_tab.BottomTabAdapter
import app.itgungnir.kwa.home.HomeFragment
import app.itgungnir.kwa.mine.MineFragment
import app.itgungnir.kwa.project.ProjectFragment
import app.itgungnir.kwa.structure.StructureFragment
import app.itgungnir.kwa.weixin.WeixinFragment
import kotlinx.android.synthetic.main.activity_main.*
import my.itgungnir.apt.router.annotation.Route
import org.jetbrains.anko.toast
import org.joda.time.DateTime

@Route("main")
class MainActivity : AppCompatActivity() {

    private var lastTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent()
    }

    private fun initComponent() {
        bottomTab.setAdapter(object : BottomTabAdapter(R.id.fragment, tabs(), supportFragmentManager) {
            override fun pages(): HashMap<String, Fragment> = hashMapOf(
                "首页" to HomeFragment(),
                "知识体系" to StructureFragment(),
                "公众号" to WeixinFragment(),
                "项目" to ProjectFragment(),
                "我的" to MineFragment()
            )
        })
    }

    private fun tabs() = listOf(
        BottomTab("首页", "\ue703", "\ue702"),
        BottomTab("知识体系", "\ue6ef", "\ue6ee"),
        BottomTab("公众号", "\ue82c", "\ue608"),
        BottomTab("项目", "\ue6ec", "\ue6eb"),
        BottomTab("我的", "\ue716", "\ue715")
    )

    override fun onBackPressed() {
        val currTime = DateTime.now().millis
        if (currTime - lastTime < 1500) {
            this.finish()
        } else {
            this.lastTime = currTime
            toast("再按一次退出应用")
        }
    }
}
