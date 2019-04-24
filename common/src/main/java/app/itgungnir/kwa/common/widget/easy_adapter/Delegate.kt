package app.itgungnir.kwa.common.widget.easy_adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface Delegate {

    fun <T : ListItem> onCreateViewHolder(parent: ViewGroup, viewType: Int): EasyAdapter.VH<T>

    fun <T : ListItem> onBindViewHolder(
        item: T,
        holder: EasyAdapter.VH<T>,
        position: Int,
        payloads: MutableList<Any>
    )

    fun onViewRecycled(holder: RecyclerView.ViewHolder)

    fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean

    fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder)

    fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder)
}