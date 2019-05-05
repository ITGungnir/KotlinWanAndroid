package app.itgungnir.kwa.hierarchy

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import app.itgungnir.kwa.R
import app.itgungnir.kwa.tree.TreeState
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_hierarchy.*
import my.itgungnir.apt.router.annotation.Route
import my.itgungnir.rxmvvm.core.mvvm.BaseActivity

@Route("hierarchy")
class HierarchyActivity : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_hierarchy

    override fun initComponent() {
        val json = intent.extras?.getString("json")
        val vo = Gson().fromJson(json, TreeState.TreeVO::class.java)

        headBar.title(vo.name)
            .back { finish() }

        tabLayout.setupWithViewPager(viewPager)

        viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment =
                HierarchyChildFragment.newInstance(vo.children[position].id)

            override fun getCount(): Int =
                vo.children.size

            override fun getPageTitle(position: Int) =
                vo.children[position].name
        }
    }

    override fun observeVM() {
    }
}