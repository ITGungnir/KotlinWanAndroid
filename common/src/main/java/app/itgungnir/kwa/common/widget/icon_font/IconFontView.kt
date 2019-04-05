package app.itgungnir.kwa.common.widget.icon_font

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

class IconFontView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    init {
        apply {
            typeface = Typeface.createFromAsset(context.assets, "iconfont.ttf")
        }
    }
}