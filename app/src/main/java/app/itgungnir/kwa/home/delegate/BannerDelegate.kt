package app.itgungnir.kwa.home.delegate

import android.annotation.SuppressLint
import android.view.View
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.WebActivity
import app.itgungnir.kwa.common.util.GlideApp
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.home.HomeState
import kotlinx.android.synthetic.main.listitem_banner.view.*
import my.itgungnir.apt.router.api.Router

class BannerDelegate : BaseDelegate<HomeState.BannerVO>() {

    override fun layoutId(): Int = R.layout.listitem_banner

    @SuppressLint("SetTextI18n")
    override fun onCreateVH(container: View) {

        container.apply {
            banner.bind(
                layoutId = R.layout.listitem_banner_child,
                items = listOf(),
                render = { _, view, data: HomeState.BannerVO.BannerItem ->
                    GlideApp.with(view.context)
                        .load(data.url)
                        .placeholder(R.mipmap.img_placeholder)
                        .error(R.mipmap.img_placeholder)
                        .centerCrop()
                        .into(view.findViewById(R.id.image))
                },
                onClick = { _, data ->
                    Router.instance.with(context)
                        .target(WebActivity)
                        .addParam("title", data.title)
                        .addParam("url", data.url)
                        .go()
                },
                onPageChange = { position, totalCount, data ->
                    title.text = data.title
                    index.text = "${position + 1}/$totalCount"
                }
            )
        }
    }

    override fun onBindVH(
        item: HomeState.BannerVO,
        holder: EasyAdapter.VH<HomeState.BannerVO>,
        position: Int,
        payloads: MutableList<Any>
    ) {

        holder.render(item) {
            banner.update(item.items)
        }
    }
}