package my.itgungnir.kotlin.wan.android.home

import my.itgungnir.kotlin.wan.android.R
import my.itgungnir.rxmvvm.core.ext.createVM
import my.itgungnir.rxmvvm.core.mvvm.BaseFragment

class HomeFragment : BaseFragment<HomeVM>() {

    override fun contentView(): Int = R.layout.fragment_home

    override fun shouldBindLifecycle(): Boolean = true

    override fun obtainVM(): HomeVM = createVM()

    override fun initComponent() {
    }

    override fun observeVM() {
    }
}