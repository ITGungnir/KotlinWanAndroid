package app.itgungnir.kwa.support.schedule.menu

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import app.itgungnir.kwa.support.R
import app.itgungnir.kwa.support.schedule.ScheduleState
import kotlinx.android.synthetic.main.view_schedule_menu.view.*
import my.itgungnir.ui.easy_adapter.EasyAdapter
import my.itgungnir.ui.easy_adapter.bind

class MenuView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr) {

    var selectCallback: ((position: Int, title: String, rentType: Int?) -> Unit)? = null

    private var dataList = mutableListOf<ScheduleState.MenuTabVO>()

    private var listAdapter: EasyAdapter? = null

    init {
        View.inflate(context, R.layout.view_schedule_menu, this)

        listAdapter = list.bind(
            delegate = MenuItemDelegate(clickCallback = { position, vo ->
                dataList = dataList.mapIndexed { index, item ->
                    item.copy(selected = index == position)
                }.toMutableList()
                listAdapter?.update(dataList)
                selectCallback?.invoke(position, vo.title, vo.value)
            }),
            diffAnalyzer = menuItemDiffer
        )

        listAdapter?.update(dataList)
    }

    fun bind(vararg items: ScheduleState.MenuTabVO) {
        dataList.clear()
        dataList.addAll(items)
        listAdapter?.update(dataList)
    }
}