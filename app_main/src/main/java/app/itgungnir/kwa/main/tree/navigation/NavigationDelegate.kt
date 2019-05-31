package app.itgungnir.kwa.main.tree.navigation

import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.common.WebActivity
import app.itgungnir.kwa.main.R
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.list_item_navigation_right.view.*
import my.itgungnir.grouter.api.Router
import my.itgungnir.ui.easy_adapter.BaseDelegate
import my.itgungnir.ui.easy_adapter.EasyAdapter
import my.itgungnir.ui.html
import my.itgungnir.ui.onAntiShakeClick

class NavigationDelegate : BaseDelegate<NavigationState.NavigationVO>() {

    override fun layoutId(): Int = R.layout.list_item_navigation_right

    override fun onCreateVH(container: View) {

        container.apply {
            childrenView.bind<NavigationState.NavigationVO.NavTagVO>(
                layoutId = R.layout.list_item_tag,
                render = { view, data ->
                    view.findViewById<Chip>(R.id.tagView).apply {
                        text = data.name
                        onAntiShakeClick(2000L) {
                            Router.instance.with(context)
                                .target(WebActivity)
                                .addParam("id", data.id)
                                .addParam("originId", data.originId)
                                .addParam("title", data.name)
                                .addParam("url", data.link)
                                .go()
                        }
                    }
                }
            )
        }
    }

    override fun onBindVH(
        item: NavigationState.NavigationVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {

            titleView.text = html(item.title)

            childrenView.refresh(item.children)
        }
    }
}