package app.itgungnir.kwa.common.widget.easy_adapter

import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer

class EasyAdapter<T : ListItem>(
    private val recyclerView: RecyclerView,
    private val diffAnalyzer: Differ<T>? = null
) : RecyclerView.Adapter<EasyAdapter.VH<T>>() {

    private val bindMap = mutableListOf<BindMap<T>>()

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            diffAnalyzer?.areItemsTheSame(oldItem, newItem) ?: (oldItem == newItem)

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            diffAnalyzer?.areContentsTheSame(oldItem, newItem) ?: (oldItem == newItem)

        override fun getChangePayload(oldItem: T, newItem: T): Any? =
            diffAnalyzer?.getChangePayload(oldItem, newItem) ?: super.getChangePayload(oldItem, newItem)
    })

    private val items
        get() = differ.currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        bindMap.first { it.type == viewType }.delegate.onCreateViewHolder<T>(parent)

    override fun onBindViewHolder(holder: VH<T>, position: Int) {
        bindMap.first { it.type == holder.itemViewType }.delegate.onBindViewHolder<T>(
            items[position], holder, position, mutableListOf()
        )
    }

    override fun getItemViewType(position: Int) =
        bindMap.first { bindMap -> bindMap.isForViewTpe(items[position]) }.type

    override fun getItemCount(): Int = items.count()

    override fun onViewRecycled(holder: VH<T>) =
        bindMap.first { it.type == holder.itemViewType }.delegate.onViewRecycled(holder)

    override fun onFailedToRecycleView(holder: VH<T>) =
        bindMap.first { it.type == holder.itemViewType }.delegate.onFailedToRecycleView(holder)

    override fun onViewAttachedToWindow(holder: VH<T>) =
        bindMap.first { it.type == holder.itemViewType }.delegate.onViewAttachedToWindow(holder)

    override fun onViewDetachedFromWindow(holder: VH<T>) =
        bindMap.first { it.type == holder.itemViewType }.delegate.onViewDetachedFromWindow(holder)

    fun map(isForViewType: (data: T) -> Boolean, delegate: Delegate): EasyAdapter<T> {
        bindMap.add(BindMap(bindMap.count(), isForViewType, delegate))
        recyclerView.adapter = this
        return this
    }

    fun update(newItems: List<T>) {
        differ.submitList(newItems)
    }

    private class BindMap<T : ListItem>(
        val type: Int,
        val isForViewTpe: (data: T) -> Boolean,
        val delegate: Delegate
    )

    open class VH<T : ListItem>(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun render(data: T, render: View.(data: T) -> Unit) {
            containerView.apply { render(data) }
        }
    }
}