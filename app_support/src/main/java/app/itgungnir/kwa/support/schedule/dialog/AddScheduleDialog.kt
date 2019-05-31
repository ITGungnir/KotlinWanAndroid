package app.itgungnir.kwa.support.schedule.dialog

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import app.itgungnir.kwa.support.R
import app.itgungnir.kwa.support.schedule.ScheduleState
import app.itgungnir.kwa.support.schedule.ScheduleViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.dialog_schedule.*
import my.itgungnir.rxmvvm.core.mvvm.buildActivityViewModel
import my.itgungnir.ui.dialog.NoTitleDialogFragment
import my.itgungnir.ui.hideSoftInput
import my.itgungnir.ui.onAntiShakeClick
import org.joda.time.DateTime

class AddScheduleDialog : NoTitleDialogFragment() {

    private val viewModel by lazy {
        buildActivityViewModel(
            activity = activity!!,
            viewModelClass = ScheduleViewModel::class.java
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.dialog_schedule, container, false)

    @SuppressLint("SetTextI18n", "CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        headBar.title("添加日程")
            .back(getString(R.string.icon_back)) { this.dismiss() }

        dateInput.onAntiShakeClick(2000L) {
            it.hideSoftInput()
            val currDate = DateTime()
            DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, year, month, day ->
                dateInput.text = DateTime(year, month + 1, day, 0, 0).toString("yyyy-MM-dd")
            }, currDate.year, currDate.monthOfYear - 1, currDate.dayOfMonth).apply {
                datePicker.minDate = currDate.millis
            }.show()
        }

        typeGroup.apply {
            check(R.id.chipWork)
            setOnCheckedChangeListener { _, id ->
                this.hideSoftInput()
                typeTip.text = id.toString()
            }
        }

        priorityGroup.apply {
            check(R.id.chipImportant)
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
            disabled("提交")
            setOnClickListener {
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
                viewModel.addSchedule(title, content, date, type, priority)
            }
        }

        observeVM()
    }

    private fun observeVM() {

        viewModel.pick(ScheduleState::dismissFlag)
            .observe(this, Observer { dismissFlag ->
                dismissFlag?.a?.let {
                    viewModel.getScheduleList()
                    this.dismiss()
                }
            })
    }
}