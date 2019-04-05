package app.itgungnir.kwa.project

import app.itgungnir.kwa.R
import my.itgungnir.rxmvvm.core.ext.createVM
import my.itgungnir.rxmvvm.core.mvvm.BaseFragment

class ProjectFragment : BaseFragment<ProjectVM>() {

    override fun contentView(): Int = R.layout.fragment_project

    override fun shouldBindLifecycle(): Boolean = true

    override fun obtainVM(): ProjectVM = createVM()

    override fun initComponent() {
    }

    override fun observeVM() {
    }
}