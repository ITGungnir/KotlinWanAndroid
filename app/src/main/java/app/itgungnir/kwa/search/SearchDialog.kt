package app.itgungnir.kwa.search

import androidx.lifecycle.Observer
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.SearchResultActivity
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.common.redux.AddSearchHistory
import app.itgungnir.kwa.common.redux.AppRedux
import app.itgungnir.kwa.common.widget.dialog.FullScreenDialog
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.common.widget.easy_adapter.bind
import app.itgungnir.kwa.search.delegate.SearchHistoryDelegate
import app.itgungnir.kwa.search.delegate.SearchHotKeyDelegate
import kotlinx.android.synthetic.main.dialog_search.*
import my.itgungnir.apt.router.api.Router
import my.itgungnir.rxmvvm.core.mvvm.buildFragmentViewModel

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
        searchBar.back { this.dismiss() }
            .doOnSearch {
                if (it.isNotBlank()) {
                    AppRedux.instance.dispatch(AddSearchHistory(it), true)
                    navigate(it)
                }
            }
            .hint("发现更多干货")

        listAdapter = list.bind()
            .map(isForViewType = { data -> data is SearchState.SearchHotKeyVO }, delegate = SearchHotKeyDelegate {
                AppRedux.instance.dispatch(AddSearchHistory(it), true)
                navigate(it)
            })
            .map(isForViewType = { data -> data is SearchState.SearchHistoryVO }, delegate = SearchHistoryDelegate {
                navigate(it)
            })

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