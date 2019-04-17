package app.itgungnir.kwa.common.widget.recycler_list

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

fun RecyclerView.bind(
    manager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    diffAnalyzer: DiffAnalyzer<ItemData>? = null
): RecyclerListAdapter {
    layoutManager = manager
    return RecyclerListAdapter(recyclerView = this, diffAnalyzer = diffAnalyzer)
}

fun RecyclerView.bind(
    manager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    delegate: BaseDelegate,
    diffAnalyzer: DiffAnalyzer<ItemData>? = null
): RecyclerListAdapter {
    layoutManager = manager
    return RecyclerListAdapter(recyclerView = this, diffAnalyzer = diffAnalyzer)
        .map(isForViewType = { true }, delegate = delegate)
}

@Suppress("UNCHECKED_CAST")
fun RecyclerView.update(items: List<ItemData>) {
    (adapter as? RecyclerListAdapter)?.update(items)
}
