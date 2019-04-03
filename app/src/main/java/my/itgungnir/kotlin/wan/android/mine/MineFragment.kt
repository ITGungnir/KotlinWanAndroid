package my.itgungnir.kotlin.wan.android.mine

import my.itgungnir.kotlin.wan.android.R
import my.itgungnir.rxmvvm.core.ext.createVM
import my.itgungnir.rxmvvm.core.mvvm.BaseFragment

class MineFragment : BaseFragment<MineVM>() {

    override fun contentView(): Int = R.layout.fragment_mine

    override fun shouldBindLifecycle(): Boolean = true

    override fun obtainVM(): MineVM = createVM()

    override fun initComponent() {
    }

    override fun observeVM() {
    }
}