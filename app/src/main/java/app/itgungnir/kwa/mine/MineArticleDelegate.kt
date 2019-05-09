package app.itgungnir.kwa.mine

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.HierarchyActivity
import app.itgungnir.kwa.common.WebActivity
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.tree.TreeState
import com.google.gson.Gson
import kotlinx.android.synthetic.main.listitem_mine_article.view.*
import my.itgungnir.apt.router.api.Router

class MineArticleDelegate : BaseDelegate<MineState.MineArticleVO>() {

    override fun layoutId(): Int = R.layout.listitem_mine_article

    override fun onCreateVH(container: View) {
    }

    @SuppressLint("SetTextI18n")
    override fun onBindVH(
        item: MineState.MineArticleVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {

            this.setOnClickListener {
                Router.instance.with(context)
                    .target(WebActivity)
                    .addParam("id", item.id)
                    .addParam("originId", item.originId)
                    .addParam("title", item.title)
                    .addParam("url", item.link)
                    .go()
            }

            author.text = "\ue830 ${item.author}"

            category.apply {
                text = item.category
                setOnClickListener {
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

            title.text = item.title

            date.text = item.date
        }
    }
}