package app.itgungnir.kwa.support.schedule

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.support.R
import kotlinx.android.synthetic.main.list_item_schedule.view.*
import my.itgungnir.ui.color
import my.itgungnir.ui.dp2px
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

            if (payloads.isNotEmpty()) {
                val payload = payloads[0]
                payload.keySet().forEach {
                    when (it) {
                        // 优先级
                        "PL_PRIORITY" -> {
                            val priority = payload.getInt(it)
                            val colorId = when (priority) {
                                1 -> R.color.clr_priority_important
                                else -> R.color.clr_priority_normal
                            }
                            priorityView.text = RichText()
                                .append("优先级：")
                                .append(if (priority == 1) "重要" else "一般")
                                .foreColor(this.context.color(colorId))
                                .create()
                        }
                        // 类型
                        "PL_TYPE" -> typeView.apply {
                            when (payload.getInt(it)) {
                                1 -> {
                                    text = "工作"
                                    backgroundDrawable = typeDrawable(this.context, R.color.clr_type_work)
                                }
                                2 -> {
                                    text = "学习"
                                    backgroundDrawable = typeDrawable(this.context, R.color.clr_type_learn)
                                }
                                3 -> {
                                    text = "生活"
                                    backgroundDrawable = typeDrawable(this.context, R.color.clr_type_life)
                                }
                            }
                        }
                        // 描述
                        "PL_DESC" -> descView.text = RichText()
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
            } else {
                // 优先级
                val colorId = when (item.priority) {
                    1 -> R.color.clr_priority_important
                    else -> R.color.clr_priority_normal
                }
                priorityView.text = RichText()
                    .append("优先级：")
                    .append(if (item.priority == 1) "重要" else "一般")
                    .foreColor(this.context.color(colorId))
                    .create()
                // 类型
                typeView.apply {
                    when (item.type) {
                        1 -> {
                            text = "工作"
                            backgroundDrawable = typeDrawable(this.context, R.color.clr_type_work)
                        }
                        2 -> {
                            text = "学习"
                            backgroundDrawable = typeDrawable(this.context, R.color.clr_type_learn)
                        }
                        3 -> {
                            text = "生活"
                            backgroundDrawable = typeDrawable(this.context, R.color.clr_type_life)
                        }
                    }
                }
                // 描述
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
    }

    private fun typeDrawable(context: Context, colorId: Int) = GradientDrawable().apply {
        setColor(context.color(colorId))
        cornerRadius = context.dp2px(5.0F).toFloat()
    }
}