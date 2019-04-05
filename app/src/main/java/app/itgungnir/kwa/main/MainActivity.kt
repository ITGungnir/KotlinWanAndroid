package app.itgungnir.kwa.main

import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.widget.bottom_tab.BottomTabAdapter
import app.itgungnir.kwa.home.HomeFragment
import app.itgungnir.kwa.mine.MineFragment
import app.itgungnir.kwa.project.ProjectFragment
import app.itgungnir.kwa.structure.StructureFragment
import app.itgungnir.kwa.weixin.WeixinFragment
import kotlinx.android.synthetic.main.activity_main.*
import my.itgungnir.apt.router.annotation.Route
import my.itgungnir.rxmvvm.core.ext.createVM
import my.itgungnir.rxmvvm.core.mvvm.BaseActivity

@Route("main")
class MainActivity : BaseActivity<MainVM>() {

    override fun contentView(): Int = R.layout.activity_main

    override fun obtainVM(): MainVM = createVM()

    override fun initComponent() {
        bottomTab.setAdapter(object : BottomTabAdapter(R.id.fragment, vm?.tabs()!!, supportFragmentManager) {
            override fun pages(): HashMap<String, Fragment> = hashMapOf(
                "首页" to HomeFragment(),
                "知识体系" to StructureFragment(),
                "公众号" to WeixinFragment(),
                "项目" to ProjectFragment(),
                "我的" to MineFragment()
            )
        })
    }

    override fun observeVM() {
        vm?.finishState?.observe(this, Observer {
            when (it) {
                true -> this.finish()
                false -> super.dispatchError(Throwable("再按一次退出应用"))
            }
        })
    }

    override fun onBackPressed() {
        vm?.goBack()
    }
}
