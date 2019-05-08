package app.itgungnir.kwa.mine.delegate

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.LoginActivity
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.mine.MineState
import kotlinx.android.synthetic.main.listitem_mine_profile.view.*
import my.itgungnir.apt.router.api.Router

class MineProfileDelegate : BaseDelegate<MineState.MineProfileVO>() {

    override fun layoutId(): Int = R.layout.listitem_mine_profile

    override fun onCreateVH(container: View) {
    }

    @SuppressLint("SetTextI18n")
    override fun onBindVH(
        item: MineState.MineProfileVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {

            when (item.userName != null) {
                true -> {
                    profile.apply {
                        text = "用户：${item.userName}"
                        setOnClickListener(null)
                    }
                }
                else -> {
                    profile.apply {
                        text = "登录 / 注册"
                        setOnClickListener {
                            Router.instance.with(context)
                                .target(LoginActivity)
                                .go()
                        }
                    }
                }
            }

            emptyPlaceholder.apply {
                visibility = when (item.userName == null || item.isListEmpty) {
                    true -> View.VISIBLE
                    else -> View.GONE
                }

                findViewById<TextView>(R.id.tip).text = when {
                    item.userName == null -> "登录后可以查看收藏列表~"
                    item.isListEmpty -> "收藏列表为空~"
                    else -> ""
                }
            }
        }
    }
}