package app.itgungnir.kwa.support.setting.delegate

import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.support.R
import app.itgungnir.kwa.support.setting.SettingState
import kotlinx.android.synthetic.main.list_item_setting_checkable.view.*
import my.itgungnir.ui.easy_adapter.BaseDelegate
import my.itgungnir.ui.easy_adapter.EasyAdapter

class CheckableDelegate(
    private val checkCallback: (Int) -> Unit
) : BaseDelegate<SettingState.CheckableVO>() {

    override fun layoutId(): Int = R.layout.list_item_setting_checkable

    override fun onCreateVH(container: View) {
    }

    override fun onBindVH(
        item: SettingState.CheckableVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {

            checkerView.apply {
                isChecked = if (payloads.isNotEmpty()) {
                    payloads[0].getBoolean("PL_CHECKED")
                } else {
                    item.isChecked
                }
                setOnClickListener {
                    checkCallback.invoke(item.id)
                }
            }

            iconView.text = item.iconFont

            titleView.text = item.title
        }
    }
}