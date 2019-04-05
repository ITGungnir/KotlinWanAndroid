package app.itgungnir.kwa.common.widget.recycler_list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BaseDelegate {

    fun onCreateViewHolder(
        parent: ViewGroup
    ) = RecyclerListAdapter.VH(
        LayoutInflater.from(parent.context).inflate(
            layoutId(),
            parent,
            false
        )
    )

    @Suppress("UNCHECKED_CAST")
    fun onBindViewHolder(
        item: ItemData,
        holder: RecyclerListAdapter.VH,
        position: Int,
        payloads: MutableList<Any>
    ) = onBindVH(item, holder, position, payloads)

    fun onViewRecycled(holder: RecyclerView.ViewHolder) {}

    fun onFailedToRecycleView(holder: RecyclerView.ViewHolder) = false

    fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {}

    fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {}

    abstract fun layoutId(): Int

    abstract fun onBindVH(
        item: ItemData,
        holder: RecyclerListAdapter.VH,
        position: Int,
        payloads: MutableList<Any>
    )
}