package app.itgungnir.kwa.common.widget.banner

import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class BannerAdapter<T>(
    private val layoutId: Int,
    private val render: (position: Int, view: View, data: T) -> Unit,
    private val onClick: (position: Int, data: T) -> Unit
) : RecyclerView.Adapter<BannerAdapter<T>.VH>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
    })

    private val items
        get() = differ.currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val vh = VH(
            LayoutInflater.from(parent.context).inflate(
                layoutId,
                parent,
                false
            )
        )

        vh.itemView.setOnClickListener {
            val realPosition = when (vh.adapterPosition) {
                0 -> items.size - 3
                items.size - 1 -> 0
                else -> vh.adapterPosition - 1
            }
            onClick.invoke(realPosition, items[vh.adapterPosition])
        }

        return vh
    }

    fun update(newItems: List<T>) {
        differ.submitList(newItems)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val realPosition = when (position) {
            0 -> items.size - 3
            items.size - 1 -> 0
            else -> position - 1
        }
        render.invoke(realPosition, holder.itemView, items[position])
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView)
}