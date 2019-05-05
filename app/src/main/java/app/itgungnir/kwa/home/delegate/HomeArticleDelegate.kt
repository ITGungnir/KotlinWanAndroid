package app.itgungnir.kwa.home.delegate

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.R
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
                    .addParam("title", item.item.title)
                    .addParam("url", item.item.link)
                    .go()
            }

            author.text = "\ue830 ${item.item.author}"

            category.apply {
                text = "${item.item.superChapterName} / ${item.item.chapterName}"
                setOnClickListener {
                    val data = TreeState.TreeVO(
                        name = item.item.superChapterName,
                        children = listOf(
                            TreeState.TreeVO.TreeChildVO(
                                id = item.item.chapterId,
                                name = item.item.chapterName
                            )
                        )
                    )
                    val json = Gson().toJson(data)
                    Router.instance.with(context)
                        .target("hierarchy")
                        .addParam("json", json)
                        .go()
                }
            }

            title.text = item.item.title

            time.text = item.item.niceDate
        }
    }
}