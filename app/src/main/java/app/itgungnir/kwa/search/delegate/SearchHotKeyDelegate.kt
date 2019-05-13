package app.itgungnir.kwa.search.delegate

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.COLOR_ACCENT
import app.itgungnir.kwa.common.onAntiShakeClick
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.search.SearchState
import kotlinx.android.synthetic.main.list_item_search_hot.view.*
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
                        textColor = Color.parseColor(COLOR_ACCENT)
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