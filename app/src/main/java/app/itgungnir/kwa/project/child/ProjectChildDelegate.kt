package app.itgungnir.kwa.project.child

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.WebActivity
import app.itgungnir.kwa.common.load
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import kotlinx.android.synthetic.main.listitem_project_child.view.*
import my.itgungnir.apt.router.api.Router

class ProjectChildDelegate : BaseDelegate<ProjectChildState.ProjectArticleVO>() {

    override fun layoutId(): Int = R.layout.listitem_project_child

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

            this.setOnClickListener {
                Router.instance.with(context)
                    .target(WebActivity)
                    .addParam("title", item.title)
                    .addParam("url", item.link)
                    .go()
            }

            cover.load(item.cover)

            author.text = "\ue830 ${item.author}"

            repository.setOnClickListener {
                Router.instance.with(context)
                    .target(WebActivity)
                    .addParam("title", item.title)
                    .addParam("url", item.repositoryLink)
                    .go()
            }

            title.text = item.title

            desc.text = item.desc

            date.text = item.date
        }
    }
}