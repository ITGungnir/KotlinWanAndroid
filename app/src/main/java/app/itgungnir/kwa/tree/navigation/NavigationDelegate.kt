package app.itgungnir.kwa.tree.navigation

import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.WebActivity
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.listitem_navigation.view.*
import my.itgungnir.apt.router.api.Router

class NavigationDelegate : BaseDelegate<NavigationState.NavigationVO>() {

    override fun layoutId(): Int = R.layout.listitem_navigation

    override fun onCreateVH(container: View) {

        container.apply {
            children.bind<NavigationState.NavigationVO.NavTagVO>(
                layoutId = R.layout.listitem_tree_tag,
                render = { view, data ->
                    view.findViewById<Chip>(R.id.tag).apply {
                        text = data.name
                        setOnClickListener {
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

            title.text = item.title

            children.refresh(item.children)
        }
    }
}