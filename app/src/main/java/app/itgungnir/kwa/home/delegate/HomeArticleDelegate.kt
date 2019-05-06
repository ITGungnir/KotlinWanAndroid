package app.itgungnir.kwa.home.delegate

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.HierarchyActivity
import app.itgungnir.kwa.common.WebActivity
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.home.HomeState
import app.itgungnir.kwa.tree.TreeState
import com.google.gson.Gson
import kotlinx.android.synthetic.main.listitem_home.view.*
import my.itgungnir.apt.router.api.Router

class HomeArticleDelegate : BaseDelegate<HomeState.ArticleVO>() {

    override fun layoutId(): Int = R.layout.listitem_home

    override fun onCreateVH(container: View) {
    }

    @SuppressLint("SetTextI18n")
    override fun onBindVH(
        item: HomeState.ArticleVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {

            this.setOnClickListener {
                Router.instance.with(context)
                    .target(WebActivity)
                    .addParam("title", item.title)
                    .addParam("url", item.link)
                    .go()
            }

            author.text = "\ue830 ${item.author}"

            category.apply {
                text = item.category
                setOnClickListener {
                    val categories = item.category.split(" / ")
                    val data = TreeState.TreeVO(
                        name = categories[0],
                        children = listOf(
                            TreeState.TreeVO.TreeChildVO(
                                id = item.categoryId,
                                name = categories[1]
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