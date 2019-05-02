package app.itgungnir.kwa.home.delegate

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.WebActivity
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.home.HomeState
import kotlinx.android.synthetic.main.listitem_article.view.*
import my.itgungnir.apt.router.api.Router

class HomeArticleDelegate : BaseDelegate<HomeState.ArticleVO>() {

    override fun layoutId(): Int = R.layout.listitem_article

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
                setOnClickListener { }
            }

            title.text = item.item.title

            time.text = item.item.niceDate
        }
    }
}