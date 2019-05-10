package app.itgungnir.kwa.common.widget.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import app.itgungnir.kwa.common.R
import app.itgungnir.kwa.common.onAntiShakeClick
import kotlinx.android.synthetic.main.view_simple_dialog.*

class SimpleDialog(
    private val msg: String,
    private val onConfirm: (() -> Unit)? = null,
    private val onCancel: (() -> Unit)? = null
) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.view_simple_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        message.text = msg

        confirm.onAntiShakeClick {
            onConfirm?.invoke()
            dismiss()
        }

        cancel.onAntiShakeClick {
            onCancel?.invoke()
            dismiss()
        }
    }
}