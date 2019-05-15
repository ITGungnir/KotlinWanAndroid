package app.itgungnir.kwa.main.mine

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.common.*
import app.itgungnir.kwa.main.R
import app.itgungnir.kwa.main.tree.TreeState
import com.google.gson.Gson
import kotlinx.android.synthetic.main.list_item_mine_article.view.*
import my.itgungnir.grouter.api.Router
import my.itgungnir.ui.easy_adapter.BaseDelegate
import my.itgungnir.ui.easy_adapter.EasyAdapter

class MineArticleDelegate(
    private val onLongClick: (id: Int, originId: Int) -> Unit
) : BaseDelegate<MineState.MineArticleVO>() {

    override fun layoutId(): Int = R.layout.list_item_mine_article

    override fun onCreateVH(container: View) {}

    @SuppressLint("SetTextI18n")
    override fun onBindVH(
        item: MineState.MineArticleVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {

            this.setOnLongClickListener {
                onLongClick.invoke(item.id, item.originId)
                true
            }

            this.onAntiShakeClick {
                Router.instance.with(context)
                    .target(WebActivity)
                    .addParam("id", item.id)
                    .addParam("originId", item.originId)
                    .addParam("title", item.title)
                    .addParam("url", item.link)
                    .go()
            }

            authorView.text = "$ICON_AUTHOR ${item.author}"

            categoryView.apply {
                text = item.category
                onAntiShakeClick {
                    val data = TreeState.TreeVO(
                        name = item.category,
                        children = listOf(
                            TreeState.TreeVO.TreeTagVO(
                                id = item.categoryId,
                                name = item.category
                            )
                        )
                    )
                    val json = Gson().toJson(data)
                    Router.instance.with(context)
                        .target(HierarchyActivity)
                        .addParam("json", json)
                        .go()
                }
            }

            titleView.text = html(item.title)

            if (payloads.isNotEmpty()) {
                val payload = payloads[0]
                payload.getString("PL_DATE")?.let {
                    dateView.text = it
                }
            } else {
                dateView.text = item.date
            }
        }
    }
}