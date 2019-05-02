package app.itgungnir.kwa.common.widget.easy_adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

class EasyAdapter(private val recyclerView: RecyclerView, private val diffAnalyzer: Differ? = null) :
    RecyclerView.Adapter<EasyAdapter.VH>() {

    private val bindMap = mutableListOf<BindMap>()

    @SuppressLint("DiffUtilEquals")
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
        bindMap.first { it.type == viewType }.delegate.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: VH, position: Int) {
        bindMap.first { it.type == holder.itemViewType }.delegate.onBindViewHolder(
            items[position], holder, position, mutableListOf()
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNullOrEmpty()) {
            this.onBindViewHolder(holder, position)
        } else {
            bindMap.first { it.type == holder.itemViewType }.delegate.onBindViewHolder(
                items[position], holder, position, payloads as MutableList<Bundle>
            )
        }
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

    fun map(isForViewType: (data: ListItem) -> Boolean, delegate: Delegate): EasyAdapter {
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
        val delegate: Delegate
    )

    open class VH(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun initialize(block: View.() -> Unit) = apply {
            containerView.apply { block() }
        }

        fun render(data: ListItem, render: View.(data: ListItem) -> Unit) {
            containerView.apply { render(data) }
        }
    }
}