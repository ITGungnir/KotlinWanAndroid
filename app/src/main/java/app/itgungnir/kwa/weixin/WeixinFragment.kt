package app.itgungnir.kwa.weixin

import app.itgungnir.kwa.R
import my.itgungnir.rxmvvm.core.ext.createVM
import my.itgungnir.rxmvvm.core.mvvm.BaseFragment

class WeixinFragment : BaseFragment<WeixinVM>() {

    override fun contentView(): Int = R.layout.fragment_weixin

    override fun shouldBindLifecycle(): Boolean = true

    override fun obtainVM(): WeixinVM = createVM()

    override fun initComponent() {
    }

    override fun observeVM() {
    }
}