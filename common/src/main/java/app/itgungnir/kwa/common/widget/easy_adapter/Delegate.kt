package app.itgungnir.kwa.common.widget.easy_adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

interface Delegate {

    fun <T : ListItem> onCreateViewHolder(parent: ViewGroup): EasyAdapter.VH<T>

    fun <T : ListItem> onBindViewHolder(
        item: T,
        holder: EasyAdapter.VH<T>,
        position: Int,
        payloads: MutableList<Any>
    )

    fun onViewRecycled(holder: RecyclerView.ViewHolder)

    fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean

    fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder)

    fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder)
}