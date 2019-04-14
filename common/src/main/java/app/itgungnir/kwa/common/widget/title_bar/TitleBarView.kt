package app.itgungnir.kwa.common.widget.title_bar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import app.itgungnir.kwa.common.R
import app.itgungnir.kwa.common.widget.icon_font.IconFontView
import kotlinx.android.synthetic.main.view_title_bar.view.*
import org.jetbrains.anko.leftPadding

class TitleBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RelativeLayout(context, attrs, defStyleAttr) {

    private var toolsLayout: LinearLayout? = null

    init {
        apply {
            View.inflate(context, R.layout.view_title_bar, this)
        }
        toolsLayout = tools
    }

    fun back(onBackPressed: () -> Unit): TitleBarView {
        back.visibility = View.VISIBLE
        back.setOnClickListener {
            onBackPressed.invoke()
        }
        title.leftPadding = 0
        return this
    }

    fun title(titleStr: String): TitleBarView {
        title.text = titleStr
        return this
    }

    fun addToolButton(iconFont: String, callback: () -> Unit): TitleBarView {
        (LayoutInflater.from(context).inflate(
            R.layout.view_icon,
            this,
            false
        ) as IconFontView).apply {
            text = iconFont
            setOnClickListener {
                callback.invoke()
            }
            toolsLayout?.addView(this)
        }
        return this
    }
}