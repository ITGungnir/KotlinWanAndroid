package app.itgungnir.kwa.structure

import app.itgungnir.kwa.R
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