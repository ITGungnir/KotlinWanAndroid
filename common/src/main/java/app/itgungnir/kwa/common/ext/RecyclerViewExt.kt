package app.itgungnir.kwa.common.ext

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import app.itgungnir.kwa.common.widget.recycler_list.BaseDelegate
import app.itgungnir.kwa.common.widget.recycler_list.ItemData
import app.itgungnir.kwa.common.widget.recycler_list.RecyclerListAdapter

fun RecyclerView.bind(
    manager: RecyclerView.LayoutManager = LinearLayoutManager(context)
): RecyclerListAdapter {
    layoutManager = manager
    return RecyclerListAdapter(this)
}

fun RecyclerView.bind(
    manager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    delegate: BaseDelegate
): RecyclerListAdapter {
    layoutManager = manager
    return RecyclerListAdapter(this).map(isForViewType = { true }, delegate = delegate)
}

@Suppress("UNCHECKED_CAST")
fun <T> RecyclerView.update(items: List<ItemData>) {
    (adapter as? RecyclerListAdapter)?.update(items)
}
