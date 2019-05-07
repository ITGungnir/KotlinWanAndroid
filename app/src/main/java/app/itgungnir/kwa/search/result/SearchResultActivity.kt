package app.itgungnir.kwa.search.result

import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.SearchResultActivity
import my.itgungnir.apt.router.annotation.Route
import my.itgungnir.rxmvvm.core.mvvm.BaseActivity

@Route(SearchResultActivity)
class SearchResultActivity : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_search_result

    override fun initComponent() {
        val key = intent.extras?.getString("key")
        println("------>>$key")
    }

    override fun observeVM() {
    }
}