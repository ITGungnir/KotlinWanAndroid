package app.itgungnir.kwa.mine

import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.widget.easy_adapter.bind
import kotlinx.android.synthetic.main.fragment_mine.*
import my.itgungnir.rxmvvm.core.mvvm.BaseFragment

class MineFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_mine

    override fun initComponent() {

        headBar.title("我的")
            .addToolButton("\ue6de") {
                // TODO TODO
            }.addToolButton("\ue728") {
                // TODO 设置
            }

        list.bind()
    }

    override fun observeVM() {
    }
}