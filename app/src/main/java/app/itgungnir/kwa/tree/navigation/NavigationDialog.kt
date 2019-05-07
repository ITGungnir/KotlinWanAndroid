package app.itgungnir.kwa.tree.navigation

import androidx.lifecycle.Observer
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.common.widget.dialog.FullScreenDialog
import app.itgungnir.kwa.common.widget.easy_adapter.bind
import app.itgungnir.kwa.common.widget.easy_adapter.update
import kotlinx.android.synthetic.main.dialog_navigation.*
import my.itgungnir.rxmvvm.core.mvvm.buildFragmentViewModel

class NavigationDialog : FullScreenDialog() {

    private val viewModel by lazy {
        buildFragmentViewModel(
            fragment = this,
            viewModelClass = NavigationViewModel::class.java
        )
    }

    override fun layoutId(): Int = R.layout.dialog_navigation

    override fun initComponent() {

        headBar.title("导航")
            .back { this.dismiss() }

        sideBar.bind(delegate = SideBarDelegate())

        navList.bind(delegate = NavigationDelegate())

        // Init data
        viewModel.getNavigationList()
    }

    override fun observeVM() {

        viewModel.pick(NavigationState::items)
            .observe(this, Observer { items ->
                items?.a?.let {
                    sideBar.update(it)
                    navList.update(it)
                }
            })

        viewModel.pick(NavigationState::error)
            .observe(this, Observer { error ->
                error?.a?.message?.let {
                    popToast(it)
                }
            })
    }
}