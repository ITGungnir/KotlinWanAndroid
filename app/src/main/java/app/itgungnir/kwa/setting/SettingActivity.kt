package app.itgungnir.kwa.setting

import android.view.View
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.ICON_BACK
import app.itgungnir.kwa.common.SettingActivity
import app.itgungnir.kwa.common.onAntiShakeClick
import app.itgungnir.kwa.common.redux.AppRedux
import app.itgungnir.kwa.common.redux.ClearUserInfo
import kotlinx.android.synthetic.main.activity_setting.*
import my.itgungnir.grouter.annotation.Route
import my.itgungnir.rxmvvm.core.mvvm.BaseActivity

@Route(SettingActivity)
class SettingActivity : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_setting

    override fun initComponent() {

        headBar.title("设置")
            .back(ICON_BACK) { finish() }

        logout.apply {
            when (AppRedux.instance.isUserIn()) {
                true -> {
                    visibility = View.VISIBLE
                    ready("退出登录")
                    onAntiShakeClick {
                        AppRedux.instance.dispatch(ClearUserInfo)
                        finish()
                    }
                }
                else -> visibility = View.GONE
            }
        }
    }

    override fun observeVM() {
    }
}