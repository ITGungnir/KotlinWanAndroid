package app.itgungnir.kwa.support.schedule

import app.itgungnir.kwa.common.ICON_BACK
import app.itgungnir.kwa.common.ICON_SCHEDULE_DONE
import app.itgungnir.kwa.common.ScheduleActivity
import app.itgungnir.kwa.support.R
import kotlinx.android.synthetic.main.activity_schedule.*
import my.itgungnir.grouter.annotation.Route
import my.itgungnir.rxmvvm.core.mvvm.BaseActivity

@Route(ScheduleActivity)
class ScheduleActivity : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_schedule

    override fun initComponent() {

        headBar.title("我的日程")
            .back(ICON_BACK) { finish() }
            .addToolButton(ICON_SCHEDULE_DONE) {}
    }

    override fun observeVM() {
    }
}