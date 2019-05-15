package app.itgungnir.kwa.support.schedule

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.support.R
import kotlinx.android.synthetic.main.list_item_schedule.view.*
import my.itgungnir.ui.easy_adapter.BaseDelegate
import my.itgungnir.ui.easy_adapter.EasyAdapter
import org.jetbrains.anko.backgroundColor

class ScheduleDelegate : BaseDelegate<ScheduleState.ScheduleVO>() {

    override fun layoutId(): Int = R.layout.list_item_schedule

    override fun onCreateVH(container: View) {
    }

    @SuppressLint("SetTextI18n")
    override fun onBindVH(
        item: ScheduleState.ScheduleVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {

            priorityView.text = when (item.priority) {
                0 -> "重要"
                else -> "一般"
            }

            typeView.apply {
                when (item.type) {
                    0 -> {
                        text = "工作"
                        backgroundColor = Color.RED
                    }
                    1 -> {
                        text = "学习"
                        backgroundColor = Color.GREEN
                    }
                    2 -> {
                        text = "生活"
                        backgroundColor = Color.BLUE
                    }
                }
            }

            descView.text = "在${item.targetDate}前，${item.title}（${item.content}）"
        }
    }
}