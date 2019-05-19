package app.itgungnir.kwa.main.home.search

import android.os.Bundle
import android.view.View
import android.widget.TextView
import app.itgungnir.kwa.common.color
import app.itgungnir.kwa.common.onAntiShakeClick
import app.itgungnir.kwa.main.R
import kotlinx.android.synthetic.main.list_item_search_hot.view.*
import my.itgungnir.ui.easy_adapter.BaseDelegate
import my.itgungnir.ui.easy_adapter.EasyAdapter
import org.jetbrains.anko.textColor

class SearchHotKeyDelegate(
    private val keyClickCallback: (String) -> Unit
) : BaseDelegate<SearchState.SearchHotKeyVO>() {

    override fun layoutId(): Int = R.layout.list_item_search_hot

    override fun onCreateVH(container: View) {
        container.apply {
            // Flex Layout
            childrenView.bind<SearchState.SearchTagVO>(
                layoutId = R.layout.list_item_tag,
                render = { view, data ->
                    view.findViewById<TextView>(R.id.tagView).apply {
                        text = data.name
                        textColor = this.context.color(R.color.colorAccent)
                        onAntiShakeClick {
                            keyClickCallback.invoke(data.name)
                        }
                    }
                }
            )
        }
    }

    override fun onBindVH(
        item: SearchState.SearchHotKeyVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {

            childrenView.refresh(item.data)
        }
    }
}