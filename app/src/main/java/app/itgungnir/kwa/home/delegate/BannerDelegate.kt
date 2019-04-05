package app.itgungnir.kwa.home.delegate

import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.widget.recycler_list.BaseDelegate
import app.itgungnir.kwa.common.widget.recycler_list.ItemData
import app.itgungnir.kwa.common.widget.recycler_list.RecyclerListAdapter
import kotlinx.android.synthetic.main.delegate_banner.view.*
import my.itgungnir.apt.router.api.Router

class BannerDelegate : BaseDelegate() {

    override fun layoutId(): Int = R.layout.delegate_banner

    override fun onBindVH(
        item: ItemData,
        holder: RecyclerListAdapter.VH,
        position: Int,
        payloads: MutableList<Any>
    ) {

        item as BannerVO

        holder.render(item) {
            banner.setAdapter(item.items) { title, url ->
                Router.instance.with(context)
                    .target("web")
                    .addParam("title", title)
                    .addParam("url", url)
                    .go()
            }
        }
    }
}