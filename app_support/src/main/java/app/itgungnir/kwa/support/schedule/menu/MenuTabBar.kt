package app.itgungnir.kwa.support.schedule.menu

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import app.itgungnir.kwa.support.R
import kotlinx.android.synthetic.main.view_schedule_menu_tab.view.*
import my.itgungnir.ui.color
import org.jetbrains.anko.textColor

class MenuTabBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    private var isTabOpen1 = false
    private var isTabOpen2 = false
    private var isTabOpen3 = false

    private val textColorNormal = context.color(R.color.text_color_head_bar)
    private val textColorSelected = context.color(R.color.colorAccent)

    init {
        View.inflate(context, R.layout.view_schedule_menu_tab, this)
    }

    fun setClickCallback(callback1: () -> Unit, callback2: () -> Unit, callback3: () -> Unit) {
        tab1.setOnClickListener {
            isTabOpen1 = !isTabOpen1
            toggleArrows(0, isTabOpen1)
            callback1.invoke()
        }

        tab2.setOnClickListener {
            isTabOpen2 = !isTabOpen2
            toggleArrows(1, isTabOpen2)
            callback2.invoke()
        }

        tab3.setOnClickListener {
            isTabOpen3 = !isTabOpen3
            callback3.invoke()
        }
    }

    private fun toggleArrows(index: Int, isOpen: Boolean) {
        listOf(tabArrow1, tabArrow2).forEachIndexed { pos, arrow ->
            arrow.text = when (pos == index && isOpen) {
                true -> context.getString(R.string.icon_arrow_up)
                else -> context.getString(R.string.icon_arrow_down)
            }
        }
    }

    fun resetArrows(index: Int? = null) {
        isTabOpen1 = 0 == index
        isTabOpen2 = 1 == index
        isTabOpen3 = 2 == index
        tabArrow1.text = when (isTabOpen1) {
            true -> context.getString(R.string.icon_arrow_up)
            else -> context.getString(R.string.icon_arrow_down)
        }
        tabArrow2.text = when (isTabOpen2) {
            true -> context.getString(R.string.icon_arrow_up)
            else -> context.getString(R.string.icon_arrow_down)
        }
    }

    fun confirmTab(index: Int, selected: Boolean, title: String? = null) {
        when (index) {
            0 -> tabText1.apply {
                textColor = if (selected) textColorSelected else textColorNormal
                text = title ?: "类型"
            }
            1 -> tabText2.apply {
                textColor = if (selected) textColorSelected else textColorNormal
                text = title ?: "优先级"
            }
            2 -> tab3.textColor = if (selected) textColorSelected else textColorNormal
        }
    }
}