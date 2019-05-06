package app.itgungnir.kwa.tree

import android.os.Bundle
import android.view.View
import android.widget.TextView
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.HierarchyActivity
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.listitem_tree.view.*
import my.itgungnir.apt.router.api.Router

class TreeDelegate : BaseDelegate<TreeState.TreeVO>() {

    override fun layoutId(): Int = R.layout.listitem_tree

    override fun onCreateVH(container: View) {
        // Flex Layout
        container.apply {
            children.bind<TreeState.TreeVO.TreeChildVO>(
                layoutId = R.layout.listitem_tree_child,
                items = listOf(),
                render = { view, data ->
                    view.findViewById<TextView>(R.id.name).text = data.name
                }
            )
        }
    }

    override fun onBindVH(
        item: TreeState.TreeVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {

            this.setOnClickListener {
                val json = Gson().toJson(item)
                Router.instance.with(context)
                    .target(HierarchyActivity)
                    .addParam("json", json)
                    .go()
            }

            title.text = item.name

            children.update(item.children)
        }
    }
}