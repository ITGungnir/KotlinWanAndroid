package app.itgungnir.kwa.common.widget.easy_adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

fun <T : ListItem> RecyclerView.bind(
    manager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    diffAnalyzer: Differ<T>? = null
): EasyAdapter<T> {
    layoutManager = manager
    return EasyAdapter(recyclerView = this, diffAnalyzer = diffAnalyzer)
}

fun <T : ListItem> RecyclerView.bind(
    manager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    delegate: BaseDelegate<T>,
    diffAnalyzer: Differ<T>? = null
): EasyAdapter<T> {
    layoutManager = manager
    return EasyAdapter(recyclerView = this, diffAnalyzer = diffAnalyzer)
        .map(isForViewType = { true }, delegate = delegate)
}

@Suppress("UNCHECKED_CAST")
fun <T : ListItem> RecyclerView.update(items: List<T>) {
    (adapter as? EasyAdapter<T>)?.update(items)
}
