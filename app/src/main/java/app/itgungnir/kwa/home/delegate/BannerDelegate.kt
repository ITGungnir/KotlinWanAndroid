package app.itgungnir.kwa.home.delegate

import android.annotation.SuppressLint
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.util.GlideApp
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.ListItem
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.home.HomeState
import kotlinx.android.synthetic.main.delegate_banner.view.*
import my.itgungnir.apt.router.api.Router

class BannerDelegate : BaseDelegate() {

    override fun layoutId(): Int = R.layout.delegate_banner

    @SuppressLint("SetTextI18n")
    override fun onBindVH(
        item: ListItem,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Any>
    ) {

        item as HomeState.BannerVO

        holder.render(item) {
            banner.bind(
                layoutId = R.layout.list_item_banner,
                items = item.items,
                render = { position, view ->
                    GlideApp.with(view.context)
                        .load(item.items[position].url)
                        .placeholder(R.mipmap.img_placeholder)
                        .error(R.mipmap.img_placeholder)
                        .centerCrop()
                        .into(view.findViewById(R.id.image))
                },
                onClick = { position ->
                    Router.instance.with(context)
                        .target("web")
                        .addParam("title", item.items[position].title)
                        .addParam("url", item.items[position].url)
                        .go()
                },
                onPageChange = { position ->
                    title.text = item.items[position].title
                    index.text = "${position + 1}/${item.items.size}"
                }
            )
        }
    }
}