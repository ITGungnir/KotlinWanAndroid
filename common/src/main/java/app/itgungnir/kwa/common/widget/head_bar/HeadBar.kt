package app.itgungnir.kwa.common.widget.head_bar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import app.itgungnir.kwa.common.R
import app.itgungnir.kwa.common.widget.icon_font.IconFontView
import kotlinx.android.synthetic.main.view_head_bar.view.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.leftPadding
import org.jetbrains.anko.textColor

class HeadBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RelativeLayout(context, attrs, defStyleAttr) {

    private var toolsLayout: LinearLayout? = null

    private var abnormal: Boolean = false

    private var textColor: Int

    init {
        View.inflate(context, R.layout.view_head_bar, this)

        context.obtainStyledAttributes(attrs, R.styleable.HeadBar).apply {
            abnormal = getBoolean(R.styleable.HeadBar_abnormal, abnormal)
            recycle()
        }

        textColor = if (abnormal) Color.parseColor("#FF707070") else Color.WHITE

        this.backgroundColor = if (abnormal) Color.WHITE else Color.parseColor("#FF707070")

        back.textColor = textColor

        title.textColor = textColor

        divider.visibility = if (abnormal) View.VISIBLE else View.GONE

        toolsLayout = tools
    }

    fun back(onBackPressed: () -> Unit): HeadBar {
        back.visibility = View.VISIBLE
        back.setOnClickListener {
            onBackPressed.invoke()
        }
        title.leftPadding = 0
        return this
    }

    fun title(titleStr: String): HeadBar {
        title.text = titleStr
        return this
    }

    fun addToolButton(iconFont: String, callback: () -> Unit): HeadBar {
        val view = LayoutInflater.from(context).inflate(R.layout.view_head_bar_icon, toolsLayout, false)
        view.findViewById<IconFontView>(R.id.icon).apply {
            text = iconFont
            this.textColor = this@HeadBar.textColor
            setOnClickListener {
                callback.invoke()
            }
        }
        toolsLayout?.addView(view)
        return this
    }

    fun updateToolButton(position: Int, iconFont: String) {
        toolsLayout?.getChildAt(position)?.let {
            (it as IconFontView).text = iconFont
        }
    }

    fun toolButtonCount() = toolsLayout?.childCount ?: 0
}