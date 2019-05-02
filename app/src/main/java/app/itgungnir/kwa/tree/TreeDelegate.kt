package app.itgungnir.kwa.tree

import android.os.Bundle
import android.view.View
import android.widget.TextView
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import kotlinx.android.synthetic.main.listitem_tree.view.*

class TreeDelegate : BaseDelegate<TreeState.TreeVO>() {

    override fun layoutId(): Int = R.layout.listitem_tree

    override fun onCreateVH(container: View) {
    }

    override fun onBindVH(
        item: TreeState.TreeVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {

            this.setOnClickListener {
                // TODO
            }

            title.text = item.item.name

            children.removeAllViews()
            item.item.children.forEach {
                children.addView(TextView(context).apply {
                    text = it.name
                })
            }
        }
    }
}