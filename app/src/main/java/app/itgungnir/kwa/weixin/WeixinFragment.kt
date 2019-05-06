package app.itgungnir.kwa.weixin

import app.itgungnir.kwa.R
import kotlinx.android.synthetic.main.fragment_weixin.*
import my.itgungnir.rxmvvm.core.mvvm.BaseFragment

class WeixinFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_weixin

    override fun initComponent() {

        searchBar.doOnSearch { }
    }

    override fun observeVM() {
    }
}