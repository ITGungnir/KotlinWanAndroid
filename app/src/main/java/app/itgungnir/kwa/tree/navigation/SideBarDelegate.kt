package app.itgungnir.kwa.tree.navigation

import android.graphics.Color
import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import kotlinx.android.synthetic.main.listitem_side_bar.view.*
import org.jetbrains.anko.backgroundColor

class SideBarDelegate : BaseDelegate<NavigationState.NavigationVO>() {

    override fun layoutId(): Int = R.layout.listitem_side_bar

    override fun onCreateVH(container: View) {
    }

    override fun onBindVH(
        item: NavigationState.NavigationVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {

            name.apply {
                text = item.title
                backgroundColor = when (item.selected) {
                    true -> Color.WHITE
                    else -> Color.parseColor("#FFF5F5F5")
                }
            }
        }
    }
}