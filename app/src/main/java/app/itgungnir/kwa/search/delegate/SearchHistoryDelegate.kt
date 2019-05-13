package app.itgungnir.kwa.search.delegate

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.COLOR_ACCENT
import app.itgungnir.kwa.common.COLOR_DIVIDER
import app.itgungnir.kwa.common.COLOR_TEXT1
import app.itgungnir.kwa.common.onAntiShakeClick
import app.itgungnir.kwa.common.redux.AppRedux
import app.itgungnir.kwa.common.redux.ClearSearchHistory
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.common.widget.flex.FlexView
import app.itgungnir.kwa.common.widget.status_view.StatusView
import app.itgungnir.kwa.search.SearchState
import kotlinx.android.synthetic.main.list_item_search_history.view.*
import org.jetbrains.anko.textColor

class SearchHistoryDelegate(
    private val keyClickCallback: (String) -> Unit
) : BaseDelegate<SearchState.SearchHistoryVO>() {

    override fun layoutId(): Int = R.layout.list_item_search_history

    override fun onCreateVH(container: View) {
        container.apply {
            // Clear Button
            clearView.onAntiShakeClick {
                AppRedux.instance.dispatch(ClearSearchHistory)
                statusView.empty { }
                clearView.apply {
                    isEnabled = false
                    textColor = Color.parseColor(COLOR_DIVIDER)
                }
            }
            // Status View
            statusView.addDelegate(StatusView.Status.SUCCEED, R.layout.status_view_flex) {
                it.findViewById<FlexView>(R.id.children).bind<SearchState.SearchTagVO>(
                    layoutId = R.layout.list_item_tag,
                    render = { view, data ->
                        view.findViewById<TextView>(R.id.tagView).apply {
                            text = data.name
                            textColor = Color.parseColor(COLOR_TEXT1)
                            onAntiShakeClick {
                                keyClickCallback.invoke(data.name)
                            }
                        }
                    }
                )
            }.addDelegate(StatusView.Status.EMPTY, R.layout.status_view_flex_empty) {
                it.findViewById<TextView>(R.id.tip).text = "快来搜点干货吧( •̀ ω •́ )✧"
            }
        }
    }

    override fun onBindVH(
        item: SearchState.SearchHistoryVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {

            if (item.data.isEmpty()) {
                statusView.empty { }
                clearView.apply {
                    isEnabled = false
                    textColor = Color.parseColor(COLOR_DIVIDER)
                }
            } else {
                statusView.succeed {
                    (it as FlexView).refresh(item.data)
                }
                clearView.apply {
                    isEnabled = true
                    textColor = Color.parseColor(COLOR_ACCENT)
                }
            }
        }
    }
}