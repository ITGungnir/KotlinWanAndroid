package app.itgungnir.kwa.main.weixin.child

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.common.WebActivity
import app.itgungnir.kwa.main.R
import kotlinx.android.synthetic.main.list_item_weixin_article.view.*
import my.itgungnir.grouter.api.Router
import my.itgungnir.ui.easy_adapter.BaseDelegate
import my.itgungnir.ui.easy_adapter.EasyAdapter
import my.itgungnir.ui.hideSoftInput
import my.itgungnir.ui.html
import my.itgungnir.ui.onAntiShakeClick

class WeixinChildDelegate : BaseDelegate<WeixinChildState.WeixinArticleVO>() {

    override fun layoutId(): Int = R.layout.list_item_weixin_article

    override fun onCreateVH(container: View) {
    }

    @SuppressLint("SetTextI18n")
    override fun onBindVH(
        item: WeixinChildState.WeixinArticleVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {

            this.onAntiShakeClick(2000L) {
                it.hideSoftInput()
                Router.instance.with(context)
                    .target(WebActivity)
                    .addParam("id", item.id)
                    .addParam("originId", item.originId)
                    .addParam("title", item.title)
                    .addParam("url", item.link)
                    .go()
            }

            authorView.text = "${context.getString(R.string.icon_author)} ${item.author}"

            titleView.text = html(item.title)

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