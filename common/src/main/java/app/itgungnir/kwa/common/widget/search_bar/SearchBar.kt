package app.itgungnir.kwa.common.widget.search_bar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import app.itgungnir.kwa.common.R
import app.itgungnir.kwa.common.onAntiShakeClick
import kotlinx.android.synthetic.main.view_search_bar.view.*

class SearchBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_search_bar, this)
    }

    fun back(block: () -> Unit): SearchBar {
        back.apply {
            visibility = View.VISIBLE
            onAntiShakeClick {
                block.invoke()
            }
        }
        return this
    }

    fun doOnSearch(block: (String) -> Unit): SearchBar {
        search.onAntiShakeClick {
            val input = inputBar.editableText.toString().trim()
            block.invoke(input)
        }
        return this
    }

    fun hint(hint: String) {
        inputBar.hint = hint
    }

    fun clear() {
        inputBar.setText("")
    }
}