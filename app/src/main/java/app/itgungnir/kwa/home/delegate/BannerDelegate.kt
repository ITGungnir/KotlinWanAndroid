package app.itgungnir.kwa.home.delegate

import android.annotation.SuppressLint
import android.view.View
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.util.GlideApp
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.home.HomeState
import kotlinx.android.synthetic.main.delegate_banner.view.*
import my.itgungnir.apt.router.api.Router

class BannerDelegate : BaseDelegate<HomeState.BannerVO>() {

    override fun layoutId(): Int = R.layout.delegate_banner

    @SuppressLint("SetTextI18n")
    override fun onCreateVH(containerView: View) {
        containerView.banner.bind<HomeState.BannerVO.BannerItem>(
            layoutId = R.layout.list_item_banner,
            items = listOf(),
            render = { position, view, data ->
                GlideApp.with(view.context)
                    .load(data.url)
                    .placeholder(R.mipmap.img_placeholder)
                    .error(R.mipmap.img_placeholder)
                    .centerCrop()
                    .into(view.findViewById(R.id.image))
            },
            onClick = { position, data ->
                Router.instance.with(containerView.context)
                    .target("web")
                    .addParam("title", data.title)
                    .addParam("url", data.url)
                    .go()
            },
            onPageChange = { position, totalCount, data ->
                containerView.title.text = data.title
                containerView.index.text = "${position + 1}/$totalCount"
            }
        )
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