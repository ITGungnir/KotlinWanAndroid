package app.itgungnir.kwa.common.widget.flex

import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.common.widget.easy_adapter.ListItem

class FlexDelegate<T : ListItem>(val layoutId: Int, val render: (view: View, data: T) -> Unit) : BaseDelegate<T>() {

    override fun layoutId(): Int = layoutId

    override fun onCreateVH(container: View) {
    }

    override fun onBindVH(item: T, holder: EasyAdapter.VH, position: Int, payloads: MutableList<Bundle>) {

        holder.render(item) {
            render.invoke(this, item)
        }
    }
}