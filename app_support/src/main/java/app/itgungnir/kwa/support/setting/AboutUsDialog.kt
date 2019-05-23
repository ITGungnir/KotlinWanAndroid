package app.itgungnir.kwa.support.setting

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import app.itgungnir.kwa.common.redux.AppRedux
import app.itgungnir.kwa.support.R
import kotlinx.android.synthetic.main.dialog_about_us.*

class AboutUsDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.dialog_about_us, container, false)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        versionInfo.text = "V${AppRedux.instance.currState().version}"
    }
}