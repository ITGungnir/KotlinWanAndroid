package app.itgungnir.kwa.main.project

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.main.R
import app.itgungnir.kwa.main.project.child.ProjectChildFragment
import kotlinx.android.synthetic.main.fragment_project.*
import my.itgungnir.rxmvvm.core.mvvm.BaseFragment
import my.itgungnir.rxmvvm.core.mvvm.buildFragmentViewModel

class ProjectFragment : BaseFragment() {

    private val viewModel by lazy {
        buildFragmentViewModel(
            fragment = this,
            viewModelClass = ProjectViewModel::class.java
        )
    }

    override fun layoutId(): Int = R.layout.fragment_project

    override fun initComponent() {

        headBar.title("项目")

        tabLayout.setupWithViewPager(viewPager)

        // Init Data
        viewModel.getProjectTabs()
    }

    override fun observeVM() {

        viewModel.pick(ProjectState::tabs)
            .observe(this, Observer { tabs ->
                tabs?.a?.let {
                    viewPager.adapter = object : FragmentStatePagerAdapter(childFragmentManager) {
                        override fun getItem(position: Int): Fragment =
                            ProjectChildFragment.newInstance(it[position].id)

                        override fun getCount(): Int =
                            it.size

                        override fun getPageTitle(position: Int) =
                            it[position].name
                    }
                }
            })

        viewModel.pick(ProjectState::error)
            .observe(this, Observer { error ->
                error?.a?.message?.let {
                    popToast(it)
                }
            })
    }
}