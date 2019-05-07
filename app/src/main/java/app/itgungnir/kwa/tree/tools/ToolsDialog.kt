package app.itgungnir.kwa.tree.tools

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.widget.TextView
import androidx.lifecycle.Observer
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.WebActivity
import app.itgungnir.kwa.common.dp2px
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.common.widget.dialog.FullScreenDialog
import kotlinx.android.synthetic.main.dialog_tools.*
import my.itgungnir.apt.router.api.Router
import my.itgungnir.rxmvvm.core.mvvm.buildFragmentViewModel
import org.jetbrains.anko.backgroundDrawable

class ToolsDialog : FullScreenDialog() {

    private val viewModel by lazy {
        buildFragmentViewModel(
            fragment = this,
            viewModelClass = ToolsViewModel::class.java
        )
    }

    override fun layoutId(): Int = R.layout.dialog_tools

    override fun initComponent() {

        back.setOnClickListener { this.dismiss() }

        children.bind<ToolsState.ToolTagVO>(
            layoutId = R.layout.listitem_tree_tag,
            render = { view, data ->
                view.findViewById<TextView>(R.id.tag).apply {
                    text = data.name
                    backgroundDrawable = GradientDrawable().apply {
                        cornerRadius = context.dp2px(15.0F).toFloat()
                        setColor(Color.parseColor("#FFF5F5F5"))
                    }
                    setOnClickListener {
                        Router.instance.with(context)
                            .target(WebActivity)
                            .addParam("title", data.name)
                            .addParam("url", data.link)
                            .go()
                    }
                }
            }
        )

        // Init data
        viewModel.getTools()
    }

    override fun observeVM() {

        viewModel.pick(ToolsState::items)
            .observe(this, Observer { items ->
                items?.a?.let {
                    children.refresh(it)
                }
            })

        viewModel.pick(ToolsState::error)
            .observe(this, Observer { error ->
                error?.a?.message?.let {
                    popToast(it)
                }
            })
    }
}