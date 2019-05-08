package app.itgungnir.kwa.setting

import android.view.View
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.SettingActivity
import app.itgungnir.kwa.common.redux.AppRedux
import app.itgungnir.kwa.common.redux.ClearUserInfo
import kotlinx.android.synthetic.main.activity_setting.*
import my.itgungnir.apt.router.annotation.Route
import my.itgungnir.rxmvvm.core.mvvm.BaseActivity

@Route(SettingActivity)
class SettingActivity : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_setting

    override fun initComponent() {

        headBar.title("设置")
            .back { finish() }

        logout.apply {
            when (AppRedux.instance.currState().userName.isNullOrBlank()) {
                true -> visibility = View.GONE
                else -> {
                    visibility = View.VISIBLE
                    ready("退出登录")
                    setOnClickListener {
                        AppRedux.instance.dispatch(ClearUserInfo, true)
                        finish()
                    }
                }
            }
        }
    }

    override fun observeVM() {
    }
}