package app.itgungnir.kwa.common.widget.banner

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import app.itgungnir.kwa.common.R
import app.itgungnir.kwa.common.util.GlideApp

class BannerAdapter(private val items: List<BannerItem>) :
    RecyclerView.Adapter<BannerAdapter.VH>() {

    var listener: ((String, String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val vh = VH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_banner,
                parent,
                false
            )
        )

        vh.itemView.setOnClickListener {
            listener?.invoke(items[vh.adapterPosition].title, items[vh.adapterPosition].target)
        }

        return vh
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        GlideApp.with(holder.itemView.context)
            .load(items[position].url)
            .placeholder(R.mipmap.img_placeholder)
            .error(R.mipmap.img_placeholder)
            .centerCrop()
            .into(holder.image)
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.image)!!
    }
}