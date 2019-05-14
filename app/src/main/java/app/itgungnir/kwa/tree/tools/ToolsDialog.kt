package app.itgungnir.kwa.tree.tools

import androidx.lifecycle.Observer
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.ICON_BACK
import app.itgungnir.kwa.common.WebActivity
import app.itgungnir.kwa.common.onAntiShakeClick
import app.itgungnir.kwa.common.popToast
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.dialog_tools.*
import my.itgungnir.grouter.api.Router
import my.itgungnir.rxmvvm.core.mvvm.buildFragmentViewModel
import my.itgungnir.ui.dialog.FullScreenDialog

class ToolsDialog : FullScreenDialog() {

    private val viewModel by lazy {
        buildFragmentViewModel(
            fragment = this,
            viewModelClass = ToolsViewModel::class.java
        )
    }

    override fun layoutId(): Int = R.layout.dialog_tools

    override fun initComponent() {

        headBar.title("常用网站")
            .back(ICON_BACK) { this.dismiss() }

        children.bind<ToolsState.ToolTagVO>(
            layoutId = R.layout.list_item_tag,
            render = { view, data ->
                view.findViewById<Chip>(R.id.tagView).apply {
                    text = data.name
                    onAntiShakeClick {
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