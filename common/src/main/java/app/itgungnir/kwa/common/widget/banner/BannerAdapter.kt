package app.itgungnir.kwa.common.widget.banner

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.itgungnir.kwa.common.onAntiShakeClick

class BannerAdapter(
    private val layoutId: Int,
    private val render: (position: Int, view: View, data: Any) -> Unit,
    private val onClick: (position: Int, data: Any) -> Unit
) : RecyclerView.Adapter<BannerAdapter.VH>() {

    @SuppressLint("DiffUtilEquals")
    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem
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

        vh.itemView.onAntiShakeClick {
            val realPosition = when (vh.adapterPosition) {
                0 -> items.size - 3
                items.size - 1 -> 0
                else -> vh.adapterPosition - 1
            }
            onClick.invoke(realPosition, items[vh.adapterPosition])
        }

        return vh
    }

    fun update(newItems: List<Any>) {
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