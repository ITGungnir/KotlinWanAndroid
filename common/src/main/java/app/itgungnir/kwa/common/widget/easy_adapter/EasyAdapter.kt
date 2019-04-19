package app.itgungnir.kwa.common.widget.easy_adapter

import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer

class EasyAdapter(
    private val recyclerView: RecyclerView,
    private val diffAnalyzer: Differ<ListItem>? = null
) : RecyclerView.Adapter<EasyAdapter.VH>() {

    private val bindMap = mutableListOf<BindMap>()

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<ListItem>() {

        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
            diffAnalyzer?.areItemsTheSame(oldItem, newItem) ?: (oldItem == newItem)

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
            diffAnalyzer?.areContentsTheSame(oldItem, newItem) ?: (oldItem == newItem)

        override fun getChangePayload(oldItem: ListItem, newItem: ListItem): Any? =
            diffAnalyzer?.getChangePayload(oldItem, newItem) ?: super.getChangePayload(oldItem, newItem)
    })

    private val items
        get() = differ.currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        bindMap.first { it.type == viewType }.delegate.onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: VH, position: Int) {
        bindMap.first { it.type == holder.itemViewType }.delegate.onBindViewHolder(
            items[position], holder, position, mutableListOf()
        )
    }

    override fun getItemViewType(position: Int) =
        bindMap.first { bindMap -> bindMap.isForViewTpe(items[position]) }.type

    override fun getItemCount(): Int = items.count()

    override fun onViewRecycled(holder: VH) =
        bindMap.first { it.type == holder.itemViewType }.delegate.onViewRecycled(holder)

    override fun onFailedToRecycleView(holder: VH) =
        bindMap.first { it.type == holder.itemViewType }.delegate.onFailedToRecycleView(holder)

    override fun onViewAttachedToWindow(holder: VH) =
        bindMap.first { it.type == holder.itemViewType }.delegate.onViewAttachedToWindow(holder)

    override fun onViewDetachedFromWindow(holder: VH) =
        bindMap.first { it.type == holder.itemViewType }.delegate.onViewDetachedFromWindow(holder)

    fun map(isForViewType: (data: ListItem) -> Boolean, delegate: BaseDelegate): EasyAdapter {
        bindMap.add(BindMap(bindMap.count(), isForViewType, delegate))
        recyclerView.adapter = this
        return this
    }

    fun update(newItems: List<ListItem>) {
        differ.submitList(newItems)
    }

    private class BindMap(
        val type: Int,
        val isForViewTpe: (data: ListItem) -> Boolean,
        val delegate: BaseDelegate
    )

    open class VH(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun render(data: ListItem, render: View.(data: ListItem) -> Unit) {
            containerView.apply { render(data) }
        }
    }
}