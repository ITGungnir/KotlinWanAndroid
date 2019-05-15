package app.itgungnir.kwa.main.tree.navigation

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.itgungnir.kwa.main.R
import app.itgungnir.kwa.common.ICON_BACK
import app.itgungnir.kwa.common.popToast
import kotlinx.android.synthetic.main.dialog_navigation.*
import my.itgungnir.rxmvvm.core.mvvm.buildFragmentViewModel
import my.itgungnir.ui.dialog.FullScreenDialog
import my.itgungnir.ui.easy_adapter.Differ
import my.itgungnir.ui.easy_adapter.ListItem
import my.itgungnir.ui.easy_adapter.bind
import my.itgungnir.ui.easy_adapter.update

class NavigationDialog : FullScreenDialog() {

    private var currIndex = 0

    private var hasTarget = false

    private val viewModel by lazy {
        buildFragmentViewModel(
            fragment = this,
            viewModelClass = NavigationViewModel::class.java
        )
    }

    override fun layoutId(): Int = R.layout.dialog_navigation

    override fun initComponent() {

        headBar.title("导航")
            .back(ICON_BACK) { this.dismiss() }

        sideBar.bind(
            delegate = SideBarDelegate { position ->
                hasTarget = true
                selectTabAt(position)
                navList.scrollToPosition(position)
            },
            diffAnalyzer = object : Differ {
                override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                    (oldItem as NavigationState.NavTabVO).name == (newItem as NavigationState.NavTabVO).name

                override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                    (oldItem as NavigationState.NavTabVO).selected == (newItem as NavigationState.NavTabVO).selected

                override fun getChangePayload(oldItem: ListItem, newItem: ListItem): Bundle? {
                    oldItem as NavigationState.NavTabVO
                    newItem as NavigationState.NavTabVO
                    val bundle = Bundle()
                    if (oldItem.selected != newItem.selected) {
                        bundle.putBoolean("PL_SELECT", newItem.selected)
                    }
                    return if (bundle.isEmpty) null else bundle
                }
            }
        )

        navList.apply {
            bind(delegate = NavigationDelegate())
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!hasTarget) {
                        val index = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        selectTabAt(index)
                    }
                    hasTarget = false
                }
            })
        }

        // Init data
        viewModel.getNavigationList()
    }

    override fun observeVM() {

        viewModel.pick(NavigationState::tabs)
            .observe(this, Observer { tabs ->
                tabs?.a?.let {
                    sideBar.update(it)
                }
            })

        viewModel.pick(NavigationState::items)
            .observe(this, Observer { items ->
                items?.a?.let {
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

    private fun selectTabAt(position: Int) {
        if (position != currIndex) {
            viewModel.setState {
                copy(
                    tabs = tabs.mapIndexed { i, item ->
                        item.copy(selected = i == position)
                    }
                )
            }
            sideBar.scrollToPosition(position)
            currIndex = position
        }
    }
}