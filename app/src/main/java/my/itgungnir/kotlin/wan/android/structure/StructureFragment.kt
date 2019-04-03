package my.itgungnir.kotlin.wan.android.structure

import my.itgungnir.kotlin.wan.android.R
import my.itgungnir.rxmvvm.core.ext.createVM
import my.itgungnir.rxmvvm.core.mvvm.BaseFragment

class StructureFragment : BaseFragment<StructureVM>() {

    override fun contentView(): Int = R.layout.fragment_structure

    override fun shouldBindLifecycle(): Boolean = true

    override fun obtainVM(): StructureVM = createVM()

    override fun initComponent() {
    }

    override fun observeVM() {
    }
}