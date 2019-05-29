package app.itgungnir.kwa.main.home.search

import androidx.lifecycle.Observer
import app.itgungnir.kwa.common.SearchResultActivity
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.common.redux.AddSearchHistory
import app.itgungnir.kwa.common.redux.AppRedux
import app.itgungnir.kwa.main.R
import kotlinx.android.synthetic.main.dialog_search.*
import my.itgungnir.grouter.api.Router
import my.itgungnir.rxmvvm.core.mvvm.buildFragmentViewModel
import my.itgungnir.ui.dialog.FullScreenDialog
import my.itgungnir.ui.easy_adapter.EasyAdapter
import my.itgungnir.ui.easy_adapter.bind

class SearchDialog : FullScreenDialog() {

    private var listAdapter: EasyAdapter? = null

    private val viewModel by lazy {
        buildFragmentViewModel(
            fragment = this,
            viewModelClass = SearchViewModel::class.java
        )
    }

    override fun layoutId(): Int = R.layout.dialog_search

    override fun initComponent() {
        searchBar.back(getString(R.string.icon_back)) { this.dismiss() }
            .doOnSearch {
                if (it.isNotBlank()) {
                    AppRedux.instance.dispatch(AddSearchHistory(it))
                    navigate(it)
                }
            }

        listAdapter = list.bind()
            .addDelegate(
                isForViewType = { data -> data is SearchState.SearchHotKeyVO },
                delegate = SearchHotKeyDelegate {
                    AppRedux.instance.dispatch(AddSearchHistory(it))
                    navigate(it)
                })
            .addDelegate(
                isForViewType = { data -> data is SearchState.SearchHistoryVO },
                delegate = SearchHistoryDelegate {
                    navigate(it)
                })
            .initialize()

        // Init data
        viewModel.initData()
    }

    override fun observeVM() {

        viewModel.pick(SearchState::items)
            .observe(this, Observer { items ->
                items?.a?.let {
                    listAdapter?.update(it)
                }
            })

        viewModel.pick(SearchState::error)
            .observe(this, Observer { error ->
                error?.a?.message?.let {
                    popToast(it)
                }
            })
    }

    private fun navigate(key: String) {
        Router.instance.with(this)
            .target(SearchResultActivity)
            .addParam("key", key)
            .go()
        this.dismiss()
    }
}