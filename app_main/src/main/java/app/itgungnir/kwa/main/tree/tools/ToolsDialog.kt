package app.itgungnir.kwa.main.tree.tools

import androidx.lifecycle.Observer
import app.itgungnir.kwa.common.WebActivity
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.main.R
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.dialog_tools.*
import my.itgungnir.grouter.api.Router
import my.itgungnir.rxmvvm.core.mvvm.buildFragmentViewModel
import my.itgungnir.ui.dialog.FullScreenDialog
import my.itgungnir.ui.onAntiShakeClick

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
            .back(getString(R.string.icon_back)) { this.dismiss() }

        children.bind<ToolsState.ToolTagVO>(
            layoutId = R.layout.list_item_tag,
            render = { view, data ->
                view.findViewById<Chip>(R.id.tagView).apply {
                    text = data.name
                    onAntiShakeClick(2000L) {
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