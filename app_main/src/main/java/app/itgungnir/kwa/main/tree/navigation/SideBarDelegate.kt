package app.itgungnir.kwa.main.tree.navigation

import android.graphics.Color
import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.common.color
import app.itgungnir.kwa.main.R
import app.itgungnir.kwa.common.onAntiShakeClick
import kotlinx.android.synthetic.main.list_item_navigation_left.view.*
import my.itgungnir.ui.easy_adapter.BaseDelegate
import my.itgungnir.ui.easy_adapter.EasyAdapter
import org.jetbrains.anko.backgroundColor

class SideBarDelegate(
    private val tabClickCallback: (Int) -> Unit
) : BaseDelegate<NavigationState.NavTabVO>() {

    override fun layoutId(): Int = R.layout.list_item_navigation_left

    override fun onCreateVH(container: View) {
    }

    override fun onBindVH(
        item: NavigationState.NavTabVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {

            this.onAntiShakeClick {
                tabClickCallback.invoke(holder.adapterPosition)
            }

            nameView.apply {
                text = item.name
                backgroundColor = if (payloads.isNullOrEmpty()) {
                    when (item.selected) {
                        true -> this.context.color(R.color.colorPure)
                        else -> this.context.color(R.color.clr_background)
                    }
                } else {
                    val payload = payloads[0]
                    when (payload["PL_SELECT"]) {
                        true -> this.context.color(R.color.colorPure)
                        else -> this.context.color(R.color.clr_background)
                    }
                }
            }
        }
    }
}