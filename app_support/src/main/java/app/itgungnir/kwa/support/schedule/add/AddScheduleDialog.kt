package app.itgungnir.kwa.support.schedule.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import app.itgungnir.kwa.common.ICON_BACK
import app.itgungnir.kwa.support.R
import kotlinx.android.synthetic.main.dialog_schedule.*

class AddScheduleDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.dialog_schedule, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        headBar.title("添加日程")
            .back(ICON_BACK) { this.dismiss() }

        confirm.disabled("提交")
    }
}