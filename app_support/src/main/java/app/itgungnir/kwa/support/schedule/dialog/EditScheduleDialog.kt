package app.itgungnir.kwa.support.schedule.dialog

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import app.itgungnir.kwa.common.ICON_BACK
import app.itgungnir.kwa.common.ICON_FINISH
import app.itgungnir.kwa.common.hideSoftInput
import app.itgungnir.kwa.common.onAntiShakeClick
import app.itgungnir.kwa.support.R
import app.itgungnir.kwa.support.schedule.ScheduleState
import app.itgungnir.kwa.support.schedule.ScheduleViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.dialog_schedule.*
import my.itgungnir.rxmvvm.core.mvvm.buildActivityViewModel
import org.joda.time.DateTime

class EditScheduleDialog(
    private val position: Int,
    private val data: ScheduleState.ScheduleVO
) : DialogFragment() {

    private val viewModel by lazy {
        buildActivityViewModel(
            activity = activity!!,
            viewModelClass = ScheduleViewModel::class.java
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.dialog_schedule, container, false)

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        headBar.title("编辑日程")
            .back(ICON_BACK) { this.dismiss() }
            .addToolButton(ICON_FINISH) {
                viewModel.finishSchedule(position, data.id)
            }

        titleInput.getInput().setText(data.title)

        contentInput.getInput().setText(data.content)

        dateInput.text = data.targetDate
        dateInput.onAntiShakeClick {
            it.hideSoftInput()
            val currDate = DateTime()
            DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, year, month, day ->
                dateInput.text = DateTime(year, month + 1, day, 0, 0).toString("yyyy-MM-dd")
            }, currDate.year, currDate.monthOfYear - 1, currDate.dayOfMonth).apply {
                datePicker.minDate = currDate.millis
            }.show()
        }

        typeGroup.apply {
            check(
                when (data.type) {
                    1 -> R.id.chipWork
                    2 -> R.id.chipLearn
                    else -> R.id.chipLife
                }
            )
            setOnCheckedChangeListener { _, id ->
                this.hideSoftInput()
                typeTip.text = id.toString()
            }
        }

        priorityGroup.apply {
            check(
                when (data.priority) {
                    1 -> R.id.chipImportant
                    else -> R.id.chipNormal
                }
            )
            setOnCheckedChangeListener { _, id ->
                this.hideSoftInput()
                priorityTip.text = id.toString()
            }
        }

        Observable.combineLatest(
            arrayOf(
                RxTextView.textChanges(titleInput.getInput()),
                RxTextView.textChanges(contentInput.getInput()),
                RxTextView.textChanges(dateInput),
                RxTextView.textChanges(typeTip),
                RxTextView.textChanges(priorityTip)
            )
        ) {
            it[0].toString().isNotBlank() && it[1].toString().isNotBlank() && it[2].toString() != "请选择完成日期" &&
                    it[3].toString() != "-1" && it[4].toString() != "-1"
        }.subscribe {
            when (it) {
                true -> confirm.ready("提交")
                else -> confirm.disabled("提交")
            }
        }

        confirm.apply {
            ready("提交")
            setOnClickListener {
                it.hideSoftInput()
                loading()
                val title = titleInput.getInput().editableText.toString().trim()
                val content = contentInput.getInput().editableText.toString().trim()
                val date = dateInput.text.toString().trim()
                val type = when (typeGroup.checkedChipId) {
                    R.id.chipWork -> 1
                    R.id.chipLearn -> 2
                    else -> 3
                }
                val priority = when (priorityGroup.checkedChipId) {
                    R.id.chipImportant -> 1
                    else -> 2
                }
                viewModel.editSchedule(data.id, title, content, date, type, priority)
            }
        }

        observeVM()
    }

    private fun observeVM() {

        viewModel.pick(ScheduleState::dismissFlag)
            .observe(this, Observer { dismissFlag ->
                dismissFlag?.a?.let {
                    this.dismiss()
                }
            })
    }
}