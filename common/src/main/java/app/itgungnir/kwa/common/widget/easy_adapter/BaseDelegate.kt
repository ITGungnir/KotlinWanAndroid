package app.itgungnir.kwa.common.widget.easy_adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseDelegate<T : ListItem> : Delegate {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EasyAdapter.VH(LayoutInflater.from(parent.context).inflate(layoutId(), parent, false)).initialize {
            onCreateVH(this)
        }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(
        item: ListItem,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) = onBindVH(item as T, holder, position, payloads)

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {}

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder) = false

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {}

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {}

    abstract fun layoutId(): Int

    abstract fun onCreateVH(container: View)

    abstract fun onBindVH(
        item: T,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    )
}