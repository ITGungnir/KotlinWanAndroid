package app.itgungnir.kwa.common.widget.easy_adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.bind(
    manager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    diffAnalyzer: Differ? = null
): EasyAdapter {
    layoutManager = manager
    return EasyAdapter(recyclerView = this, diffAnalyzer = diffAnalyzer)
}

fun RecyclerView.bind(
    manager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    delegate: Delegate,
    diffAnalyzer: Differ? = null
): EasyAdapter {
    layoutManager = manager
    return EasyAdapter(recyclerView = this, diffAnalyzer = diffAnalyzer)
        .map(isForViewType = { true }, delegate = delegate)
}

@Suppress("UNCHECKED_CAST")
fun <T : ListItem> RecyclerView.update(items: List<T>) {
    (adapter as? EasyAdapter)?.update(items)
}
