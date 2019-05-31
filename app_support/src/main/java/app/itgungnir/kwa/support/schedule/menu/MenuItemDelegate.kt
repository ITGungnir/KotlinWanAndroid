package app.itgungnir.kwa.support.schedule.menu

import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.support.R
import app.itgungnir.kwa.support.schedule.ScheduleState
import kotlinx.android.synthetic.main.list_item_menu.view.*
import my.itgungnir.ui.color
import my.itgungnir.ui.easy_adapter.BaseDelegate
import my.itgungnir.ui.easy_adapter.Differ
import my.itgungnir.ui.easy_adapter.EasyAdapter
import my.itgungnir.ui.easy_adapter.ListItem
import org.jetbrains.anko.textColor

val menuItemDiffer = object : Differ {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
        (oldItem as ScheduleState.MenuTabVO).title == (newItem as ScheduleState.MenuTabVO).title

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
        (oldItem as ScheduleState.MenuTabVO).selected == (newItem as ScheduleState.MenuTabVO).selected

    override fun getChangePayload(oldItem: ListItem, newItem: ListItem): Bundle? {
        oldItem as ScheduleState.MenuTabVO
        newItem as ScheduleState.MenuTabVO
        val payload = Bundle()
        if (oldItem.selected != newItem.selected) {
            payload.putBoolean("PL_SELECT", newItem.selected)
        }
        return if (payload.size() == 0) null else payload
    }
}

class MenuItemDelegate(
    private val clickCallback: (Int, ScheduleState.MenuTabVO) -> Unit
) : BaseDelegate<ScheduleState.MenuTabVO>() {

    override fun layoutId(): Int = R.layout.list_item_menu

    override fun onCreateVH(container: View) {
    }

    override fun onBindVH(
        item: ScheduleState.MenuTabVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {

            title.apply {
                text = item.title
                if (payloads.isNullOrEmpty()) {
                    textColor = when (item.selected) {
                        true -> context.color(R.color.colorAccent)
                        else -> context.color(R.color.text_color_level_1)
                    }
                } else {
                    val payload = payloads[0]
                    for (key in payload.keySet()) {
                        when (key) {
                            "PL_SELECT" -> textColor = when (item.selected) {
                                true -> context.color(R.color.colorAccent)
                                else -> context.color(R.color.text_color_level_1)
                            }
                        }
                    }
                }
            }

            setOnClickListener {
                clickCallback.invoke(holder.adapterPosition, item)
            }
        }
    }
}