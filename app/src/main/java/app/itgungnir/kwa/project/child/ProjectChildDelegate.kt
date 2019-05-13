package app.itgungnir.kwa.project.child

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.ICON_AUTHOR
import app.itgungnir.kwa.common.WebActivity
import app.itgungnir.kwa.common.load
import app.itgungnir.kwa.common.onAntiShakeClick
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import kotlinx.android.synthetic.main.list_item_project_article.view.*
import my.itgungnir.apt.router.api.Router

class ProjectChildDelegate : BaseDelegate<ProjectChildState.ProjectArticleVO>() {

    override fun layoutId(): Int = R.layout.list_item_project_article

    override fun onCreateVH(container: View) {
    }

    @SuppressLint("SetTextI18n")
    override fun onBindVH(
        item: ProjectChildState.ProjectArticleVO,
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

            coverView.load(item.cover)

            authorView.text = "$ICON_AUTHOR ${item.author}"

            repositoryView.onAntiShakeClick {
                Router.instance.with(context)
                    .target(WebActivity)
                    .addParam("title", item.title)
                    .addParam("url", item.repositoryLink)
                    .go()
            }

            titleView.text = item.title

            descView.text = item.desc

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