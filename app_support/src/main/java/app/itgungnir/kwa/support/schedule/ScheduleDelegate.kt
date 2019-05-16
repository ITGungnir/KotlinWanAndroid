package app.itgungnir.kwa.support.schedule

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.common.*
import app.itgungnir.kwa.support.R
import kotlinx.android.synthetic.main.list_item_schedule.view.*
import my.itgungnir.ui.easy_adapter.BaseDelegate
import my.itgungnir.ui.easy_adapter.EasyAdapter
import my.itgungnir.ui.rich_text.RichText
import org.jetbrains.anko.backgroundDrawable

class ScheduleDelegate(
    private val clickCallback: (Int, ScheduleState.ScheduleVO) -> Unit,
    private val longClickCallback: (Int, Int) -> Unit
) : BaseDelegate<ScheduleState.ScheduleVO>() {

    override fun layoutId(): Int = R.layout.list_item_schedule

    override fun onCreateVH(container: View) {
    }

    override fun onBindVH(
        item: ScheduleState.ScheduleVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {

            this.setOnLongClickListener {
                longClickCallback.invoke(holder.adapterPosition, item.id)
                true
            }

            this.setOnClickListener {
                clickCallback.invoke(holder.adapterPosition, item)
            }

            val cornerRadius = this.context.dp2px(4.0F).toFloat()

            priorityView.text = RichText()
                .append("优先级：")
                .append(if (item.priority == 1) "重要" else "一般")
                .foreColor(Color.parseColor(if (item.priority == 1) COLOR_PRIORITY_IMPORTANT else COLOR_PRIORITY_NORMAL))
                .create()

            typeView.apply {
                when (item.type) {
                    1 -> {
                        text = "工作"
                        backgroundDrawable = buildTypeDrawable(COLOR_BG_WORK, cornerRadius)
                    }
                    2 -> {
                        text = "学习"
                        backgroundDrawable = buildTypeDrawable(COLOR_BG_LEARN, cornerRadius)
                    }
                    3 -> {
                        text = "生活"
                        backgroundDrawable = buildTypeDrawable(COLOR_BG_LIFE, cornerRadius)
                    }
                }
            }

            descView.text = RichText()
                .append("在")
                .append(item.targetDate)
                .bold()
                .append("前，")
                .append(item.title)
                .bold()
                .append("（${item.content}）")
                .create()
        }
    }

    private fun buildTypeDrawable(color: String, radius: Float) = GradientDrawable().apply {
        setColor(Color.parseColor(color))
        cornerRadius = radius
    }
}