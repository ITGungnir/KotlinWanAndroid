package app.itgungnir.kwa.main.home.delegate

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import app.itgungnir.kwa.common.WebActivity
import app.itgungnir.kwa.common.html
import app.itgungnir.kwa.common.load
import app.itgungnir.kwa.common.redux.AppRedux
import app.itgungnir.kwa.main.R
import app.itgungnir.kwa.main.home.HomeState
import kotlinx.android.synthetic.main.list_item_home_banner.view.*
import my.itgungnir.grouter.api.Router
import my.itgungnir.ui.easy_adapter.BaseDelegate
import my.itgungnir.ui.easy_adapter.EasyAdapter

class BannerDelegate : BaseDelegate<HomeState.BannerVO>() {

    override fun layoutId(): Int = R.layout.list_item_home_banner

    @SuppressLint("SetTextI18n")
    override fun onCreateVH(container: View) {

        container.apply {
            bannerView.bind<HomeState.BannerVO.BannerItem>(
                layoutId = R.layout.list_item_home_banner_child,
                render = { _, view, data ->
                    view.findViewById<ImageView>(R.id.imageView).apply {
                        when (AppRedux.instance.isNoImage()) {
                            true -> load(R.mipmap.img_placeholder)
                            else -> load(data.url)
                        }
                    }
                },
                onClick = { _, data ->
                    Router.instance.with(context)
                        .target(WebActivity)
                        .addParam("title", data.title)
                        .addParam("url", data.target)
                        .go()
                },
                onPageChange = { position, totalCount, data ->
                    titleView.text = html(data.title)
                    indexView.text = "${position + 1}/$totalCount"
                }
            )
        }
    }

    override fun onBindVH(
        item: HomeState.BannerVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {
            bannerView.update(item.items)
        }
    }
}