package app.itgungnir.kwa.common.widget.bottom_tab

import android.content.Context
import android.database.DataSetObserver
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import app.itgungnir.kwa.common.R
import app.itgungnir.kwa.common.widget.icon_font.IconFontView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayout
import com.google.android.flexbox.JustifyContent
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor

class BottomTabView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FlexboxLayout(context, attrs, defStyleAttr) {

    private var selectedColor: Int = Color.parseColor("#FF707070")

    private var unselectedColor: Int = Color.parseColor("#FFC2C2C2")

    private var tabTextSize: Float = 12.0F

    private var adapter: BottomTabAdapter? = null

    init {
        apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.SPACE_AROUND
            alignItems = AlignItems.CENTER

            context.obtainStyledAttributes(attrs, R.styleable.BottomTabView).apply {
                selectedColor = getColor(R.styleable.BottomTabView_btv_color_selected, selectedColor)
                unselectedColor = getColor(R.styleable.BottomTabView_btv_color_unselected, unselectedColor)
                tabTextSize = getFloat(R.styleable.BottomTabView_btv_text_size, tabTextSize)
                recycle()
            }
        }
    }

    private val dataSetObserver = object : DataSetObserver() {
        override fun onChanged() {
            initTabs()
        }
    }

    fun setAdapter(adapter: BottomTabAdapter) {
        this.adapter = adapter.apply {
            selectTabAt(0)
        }
        this.initTabs()
    }

    private fun initTabs() {
        if (childCount != this.adapter?.tabs?.size) {
            removeAllViews()
            this.adapter?.tabs?.forEachIndexed { index, _ ->
                LayoutInflater.from(context).inflate(R.layout.list_item_bottom_tab, this, false).apply {
                    setOnClickListener { adapter?.selectTabAt(index) }
                    this@BottomTabView.addView(this)
                }
            }
        }
        this.adapter?.tabs?.forEachIndexed { index, bottomTab ->
            val item = getChildAt(index)
            // icon
            item.find<IconFontView>(R.id.icon).apply {
                when (adapter?.currIndex) {
                    index -> {
                        text = bottomTab.selectedIcon
                        textColor = selectedColor
                    }
                    else -> {
                        text = bottomTab.unselectedIcon
                        textColor = unselectedColor
                    }
                }
            }
            // title
            item.find<TextView>(R.id.title).apply {
                text = bottomTab.title
                textSize = tabTextSize
                textColor = when (adapter?.currIndex) {
                    index -> selectedColor
                    else -> unselectedColor
                }
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        this.adapter?.registerDataSetObserver(this.dataSetObserver)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        this.adapter?.unRegisterDataSetObserver(this.dataSetObserver)
    }
}