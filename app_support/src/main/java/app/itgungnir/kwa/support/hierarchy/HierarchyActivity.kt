package app.itgungnir.kwa.support.hierarchy

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import app.itgungnir.kwa.support.R
import app.itgungnir.kwa.common.HierarchyActivity
import app.itgungnir.kwa.common.ICON_BACK
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_hierarchy.*
import my.itgungnir.grouter.annotation.Route
import my.itgungnir.rxmvvm.core.mvvm.BaseActivity
import my.itgungnir.ui.easy_adapter.ListItem

@Route(HierarchyActivity)
class HierarchyActivity : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_hierarchy

    override fun initComponent() {
        val json = intent.extras?.getString("json")
        val vo = Gson().fromJson(json, TreeVO::class.java)

        headBar.title(vo.name)
            .back(ICON_BACK) { finish() }

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

    private data class TreeVO(
        val name: String,
        val children: List<TreeTagVO>
    ) : ListItem {

        data class TreeTagVO(
            val id: Int,
            val name: String
        ) : ListItem
    }
}