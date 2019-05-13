package app.itgungnir.kwa.home.delegate

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.HierarchyActivity
import app.itgungnir.kwa.common.ICON_AUTHOR
import app.itgungnir.kwa.common.WebActivity
import app.itgungnir.kwa.common.onAntiShakeClick
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.home.HomeState
import app.itgungnir.kwa.tree.TreeState
import com.google.gson.Gson
import kotlinx.android.synthetic.main.list_item_home_article.view.*
import my.itgungnir.apt.router.api.Router

class HomeArticleDelegate : BaseDelegate<HomeState.HomeArticleVO>() {

    override fun layoutId(): Int = R.layout.list_item_home_article

    override fun onCreateVH(container: View) {
    }

    @SuppressLint("SetTextI18n")
    override fun onBindVH(
        item: HomeState.HomeArticleVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {

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
                    val categories = item.category.split(" / ")
                    val data = TreeState.TreeVO(
                        name = categories[0],
                        children = listOf(
                            TreeState.TreeVO.TreeTagVO(
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

            titleView.text = item.title

            dateView.text = item.date

            topView.visibility = when (item.isTop) {
                true -> View.VISIBLE
                else -> View.GONE
            }
        }
    }
}