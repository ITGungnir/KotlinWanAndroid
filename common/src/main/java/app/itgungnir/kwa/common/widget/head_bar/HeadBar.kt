package app.itgungnir.kwa.common.widget.head_bar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import app.itgungnir.kwa.common.R
import app.itgungnir.kwa.common.dp2px
import app.itgungnir.kwa.common.widget.icon_font.IconFontView
import kotlinx.android.synthetic.main.view_head_bar.view.*
import org.jetbrains.anko.leftPadding
import org.jetbrains.anko.textColor

class HeadBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RelativeLayout(context, attrs, defStyleAttr) {

    private var toolsLayout: LinearLayout? = null

    init {
        apply {
            View.inflate(context, R.layout.view_head_bar, this)
        }
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
        IconFontView(context).apply {
            layoutParams = LinearLayout.LayoutParams(context.dp2px(50F), LinearLayout.LayoutParams.MATCH_PARENT)
            text = iconFont
            textSize = 24.0F
            textColor = Color.WHITE
            gravity = Gravity.CENTER
            setOnClickListener {
                callback.invoke()
            }
            toolsLayout?.addView(this)
        }
        return this
    }
}