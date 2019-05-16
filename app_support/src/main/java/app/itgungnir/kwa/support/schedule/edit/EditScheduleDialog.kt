package app.itgungnir.kwa.support.schedule.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import app.itgungnir.kwa.common.ICON_BACK
import app.itgungnir.kwa.common.ICON_FINISH
import app.itgungnir.kwa.support.R
import kotlinx.android.synthetic.main.dialog_schedule.*

class EditScheduleDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.dialog_schedule, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        headBar.title("编辑日程")
            .back(ICON_BACK) { this.dismiss() }
            .addToolButton(ICON_FINISH) {}

        confirm.disabled("提交")
    }
}