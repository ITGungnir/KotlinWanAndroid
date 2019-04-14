package app.itgungnir.kwa.common.widget.recycler_footer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import app.itgungnir.kwa.common.R

class FooterAdapter(
    private val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    private var status: FooterStatus.Status
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = if (adapter.itemCount > 0) {
        adapter.itemCount + 1
    } else {
        adapter.itemCount
    }

    override fun getItemViewType(position: Int): Int =
        if (position == itemCount - 1) Int.MAX_VALUE
        else adapter.getItemViewType(position)

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
        adapter.setHasStableIds(hasStableIds)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            Int.MAX_VALUE ->
                FooterViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.list_item_recycler_footer, parent, false
                    )
                )
            else ->
                adapter.onCreateViewHolder(parent, viewType)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            Int.MAX_VALUE -> (holder as FooterViewHolder).applyStatus(status)
            else -> adapter.onBindViewHolder(holder, position)
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        when (holder.itemViewType) {
            Int.MAX_VALUE -> super.onViewRecycled(holder)
            else -> adapter.onViewRecycled(holder)
        }
    }

    fun notifyStatusChanged(status: FooterStatus.Status) {
        if (this.status == status) {
            return
        } else {
            this.status = status
        }
        this.notifyDataSetChanged()
    }

    inner class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.title)!!

        fun applyStatus(status: FooterStatus.Status) {
            title.text = status.title
            itemView.invalidate()
        }
    }
}