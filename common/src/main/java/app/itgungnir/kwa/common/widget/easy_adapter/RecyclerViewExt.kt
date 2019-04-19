package app.itgungnir.kwa.common.widget.easy_adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

fun RecyclerView.bind(
    manager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    diffAnalyzer: Differ<ListItem>? = null
): EasyAdapter {
    layoutManager = manager
    return EasyAdapter(recyclerView = this, diffAnalyzer = diffAnalyzer)
}

fun RecyclerView.bind(
    manager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    delegate: BaseDelegate,
    diffAnalyzer: Differ<ListItem>? = null
): EasyAdapter {
    layoutManager = manager
    return EasyAdapter(recyclerView = this, diffAnalyzer = diffAnalyzer)
        .map(isForViewType = { true }, delegate = delegate)
}

@Suppress("UNCHECKED_CAST")
fun RecyclerView.update(items: List<ListItem>) {
    (adapter as? EasyAdapter)?.update(items)
}
