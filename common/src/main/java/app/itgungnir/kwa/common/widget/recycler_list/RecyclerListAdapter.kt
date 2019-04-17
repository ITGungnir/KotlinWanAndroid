package app.itgungnir.kwa.common.widget.recycler_list

import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer

class RecyclerListAdapter(
    private val recyclerView: RecyclerView,
    private val diffAnalyzer: DiffAnalyzer<ItemData>? = null
) : RecyclerView.Adapter<RecyclerListAdapter.VH>() {

    private val bindMap = mutableListOf<BindMap>()

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<ItemData>() {

        override fun areItemsTheSame(oldItem: ItemData, newItem: ItemData): Boolean =
            diffAnalyzer?.areItemsTheSame(oldItem, newItem) ?: (oldItem == newItem)

        override fun areContentsTheSame(oldItem: ItemData, newItem: ItemData): Boolean =
            diffAnalyzer?.areContentsTheSame(oldItem, newItem) ?: (oldItem == newItem)

        override fun getChangePayload(oldItem: ItemData, newItem: ItemData): Any? =
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

    fun map(isForViewType: (data: ItemData) -> Boolean, delegate: BaseDelegate): RecyclerListAdapter {
        bindMap.add(BindMap(bindMap.count(), isForViewType, delegate))
        recyclerView.adapter = this
        return this
    }

    fun update(newItems: List<ItemData>) {
        differ.submitList(newItems)
    }

    private class BindMap(
        val type: Int,
        val isForViewTpe: (data: ItemData) -> Boolean,
        val delegate: BaseDelegate
    )

    open class VH(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun render(data: ItemData, render: View.(data: ItemData) -> Unit) {
            containerView.apply { render(data) }
        }
    }
}