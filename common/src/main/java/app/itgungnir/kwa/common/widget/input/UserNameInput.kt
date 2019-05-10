package app.itgungnir.kwa.common.widget.input

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import app.itgungnir.kwa.common.R
import app.itgungnir.kwa.common.onAntiShakeClick
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.view_username_input.view.*

@SuppressLint("CheckResult")
class UserNameInput @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        apply {
            View.inflate(context, R.layout.view_username_input, this)
        }

        RxTextView.textChanges(username).subscribe {
            clear.visibility = if (it.isNullOrBlank()) View.GONE else View.VISIBLE
        }
        clear.onAntiShakeClick {
            username.setText("")
        }
    }

    fun getInput(): EditText = username
}