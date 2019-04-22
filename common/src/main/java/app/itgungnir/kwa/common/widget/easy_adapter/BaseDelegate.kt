package app.itgungnir.kwa.common.widget.easy_adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BaseDelegate<T : ListItem> : Delegate {

    override fun <E : ListItem> onCreateViewHolder(parent: ViewGroup) =
        EasyAdapter.VH<E>(LayoutInflater.from(parent.context).inflate(layoutId(), parent, false))

    @Suppress("UNCHECKED_CAST")
    override fun <E : ListItem> onBindViewHolder(
        item: E,
        holder: EasyAdapter.VH<E>,
        position: Int,
        payloads: MutableList<Any>
    ) = onBindVH(item as T, holder as EasyAdapter.VH<T>, position, payloads)

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {}

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder) = false

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {}

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {}

    abstract fun layoutId(): Int

    abstract fun onBindVH(
        item: T,
        holder: EasyAdapter.VH<T>,
        position: Int,
        payloads: MutableList<Any>
    )
}