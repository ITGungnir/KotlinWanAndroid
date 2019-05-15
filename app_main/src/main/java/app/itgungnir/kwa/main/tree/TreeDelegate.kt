package app.itgungnir.kwa.main.tree

import android.os.Bundle
import android.view.View
import android.widget.TextView
import app.itgungnir.kwa.main.R
import app.itgungnir.kwa.common.HierarchyActivity
import app.itgungnir.kwa.common.onAntiShakeClick
import com.google.gson.Gson
import kotlinx.android.synthetic.main.list_item_tree.view.*
import my.itgungnir.grouter.api.Router
import my.itgungnir.ui.easy_adapter.BaseDelegate
import my.itgungnir.ui.easy_adapter.EasyAdapter

class TreeDelegate : BaseDelegate<TreeState.TreeVO>() {

    override fun layoutId(): Int = R.layout.list_item_tree

    override fun onCreateVH(container: View) {
        // Flex Layout
        container.apply {
            childrenView.bind<TreeState.TreeVO.TreeTagVO>(
                layoutId = R.layout.list_item_tree_child,
                render = { view, data ->
                    view.findViewById<TextView>(R.id.nameView).text = data.name
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

            this.onAntiShakeClick {
                val json = Gson().toJson(item)
                Router.instance.with(context)
                    .target(HierarchyActivity)
                    .addParam("json", json)
                    .go()
            }

            titleView.text = item.name

            childrenView.refresh(item.children)
        }
    }
}