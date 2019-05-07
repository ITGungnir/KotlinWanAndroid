package app.itgungnir.kwa.common.widget.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment

abstract class FullScreenDialog : DialogFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)

        super.onActivityCreated(savedInstanceState)

        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(0x0F000000))
            setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(layoutId(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
        observeVM()
    }

    abstract fun layoutId(): Int

    abstract fun initComponent()

    abstract fun observeVM()
}