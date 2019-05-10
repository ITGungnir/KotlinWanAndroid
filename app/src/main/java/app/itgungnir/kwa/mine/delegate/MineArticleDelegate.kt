package app.itgungnir.kwa.mine.delegate

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.HierarchyActivity
import app.itgungnir.kwa.common.WebActivity
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.mine.MineState
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

        var id = item.id
        var originId = item.originId
        var authorStr = item.author
        var categoryStr = item.category
        var categoryId = item.categoryId
        var titleStr = item.title
        var dateStr = item.date
        var link = item.link

        holder.render(item) {

            if (payloads.isNotEmpty()) {
                val payload = payloads[0]
                id = if (payload.containsKey("PL_ID")) payload.getInt("PL_ID") else id
                originId = if (payload.containsKey("PL_OID")) payload.getInt("PL_OID") else originId
                authorStr = if (payload.containsKey("PL_AUTHOR")) payload.getString("PL_AUTHOR", "") else authorStr
                categoryStr = if (payload.containsKey("PL_C")) payload.getString("PL_C", "") else categoryStr
                categoryId = if (payload.containsKey("PL_CID")) payload.getInt("PL_CID") else categoryId
                titleStr = if (payload.containsKey("PL_TITLE")) payload.getString("PL_TITLE", "") else titleStr
                dateStr = if (payload.containsKey("PL_DATE")) payload.getString("PL_DATE", "") else dateStr
                link = if (payload.containsKey("PL_LINK")) payload.getString("PL_LINK", "") else link
            }

            this.setOnClickListener {
                Router.instance.with(context)
                    .target(WebActivity)
                    .addParam("id", id)
                    .addParam("originId", originId)
                    .addParam("title", titleStr)
                    .addParam("url", link)
                    .go()
            }

            author.text = "\ue830 $authorStr"

            category.apply {
                text = categoryStr
                setOnClickListener {
                    val data = TreeState.TreeVO(
                        name = categoryStr,
                        children = listOf(
                            TreeState.TreeVO.TreeTagVO(
                                id = categoryId,
                                name = categoryStr
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

            title.text = titleStr

            date.text = dateStr
        }
    }
}