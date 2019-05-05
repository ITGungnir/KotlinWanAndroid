package app.itgungnir.kwa.common.widget.bottom_tab

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayout
import com.google.android.flexbox.JustifyContent

class BottomTab @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FlexboxLayout(context, attrs, defStyleAttr) {

    private val currIndex = MutableLiveData<Int>().apply { value = 0 }

    private var targetFrameId: Int? = null
    private lateinit var fragmentManager: FragmentManager

    private val inflater by lazy { LayoutInflater.from(context) }

    init {
        apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.SPACE_AROUND
            alignItems = AlignItems.CENTER
        }
    }

    fun <T> init(
        targetFrameId: Int,
        fragmentManager: FragmentManager,
        items: List<Pair<T, Fragment>>,
        itemLayoutId: Int,
        render: (view: View, data: T, selected: Boolean) -> Unit
    ) {
        if (childCount > 0) {
            removeAllViews()
        }
        this.targetFrameId = targetFrameId
        this.fragmentManager = fragmentManager
        for (index in 0 until items.size) {
            val view = inflater.inflate(itemLayoutId, this, false)
            view.setOnClickListener {
                currIndex.value = index
            }
            this.addView(view)
        }
        // Observe data change
        currIndex.observe(context as LifecycleOwner, Observer {
            togglePage(it, items[it].second)
            for (index in 0 until childCount) {
                render.invoke(getChildAt(index), items[index].first, it == index)
            }
        })
    }

    private fun togglePage(newIndex: Int, targetFragment: Fragment) {
        if (targetFrameId == null) {
            return
        }
        val tag = newIndex.toString()
        val transaction = this.fragmentManager.beginTransaction()
        if (this.fragmentManager.findFragmentByTag(tag) == null) {
            transaction.add(this.targetFrameId!!, targetFragment, tag)
        }
        for (fragment in this.fragmentManager.fragments) {
            if (fragment.tag == tag) {
                transaction.show(fragment)
            } else {
                transaction.hide(fragment)
            }
        }
        transaction.commit()
    }
}