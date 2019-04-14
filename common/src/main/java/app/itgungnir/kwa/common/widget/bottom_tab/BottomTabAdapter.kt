package app.itgungnir.kwa.common.widget.bottom_tab

import android.database.DataSetObservable
import android.database.DataSetObserver
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

abstract class BottomTabAdapter(
    private val frameId: Int,
    val tabs: List<BottomTab>,
    private val manager: FragmentManager
) {

    var currIndex = -1

    private val dataSetObservable = DataSetObservable()

    fun registerDataSetObserver(observer: DataSetObserver) {
        this.dataSetObservable.registerObserver(observer)
    }

    fun unRegisterDataSetObserver(observer: DataSetObserver) {
        this.dataSetObservable.unregisterObserver(observer)
    }

    private fun notifyDataSetChanged() {
        this.dataSetObservable.notifyChanged()
    }

    fun selectTabAt(position: Int) {
        if (currIndex == position) {
            return
        }
        this.currIndex = position
        togglePage()
        notifyDataSetChanged()
    }

    private fun togglePage() {
        val tag = tabs[currIndex].title
        val transaction = manager.beginTransaction()
        if (manager.findFragmentByTag(tag) == null) {
            pages()[tag]?.let { transaction.add(frameId, it, tag) }
        }
        for (fragment in manager.fragments) {
            if (fragment.tag == tag) {
                transaction.show(fragment)
            } else {
                transaction.hide(fragment)
            }
        }
        transaction.commit()
    }

    abstract fun pages(): HashMap<String, Fragment>
}