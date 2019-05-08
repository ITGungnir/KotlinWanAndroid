package app.itgungnir.kwa.common.widget.input

import android.annotation.SuppressLint
import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import app.itgungnir.kwa.common.R
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.view_password_input.view.*

@SuppressLint("CheckResult")
class PasswordInput @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        apply {
            View.inflate(context, R.layout.view_password_input, this)
        }

        toggle.text = "\ue8d4"
        RxTextView.textChanges(password).subscribe {
            toggle.visibility = if (it.isNullOrBlank()) View.GONE else View.VISIBLE
        }
        toggle.setOnClickListener {
            if (toggle.tag == "1") {
                toggle.tag = "2"
                toggle.text = "\ue8d5"
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else if (toggle.tag == "2") {
                toggle.tag = "1"
                toggle.text = "\uE8D4"
                password.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            password.setSelection(password.text.length)
        }
    }

    fun hint(hint: String) {
        password.hint = hint
    }

    fun getInput(): EditText = password
}