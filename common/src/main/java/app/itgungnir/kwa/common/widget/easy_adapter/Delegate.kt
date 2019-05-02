package app.itgungnir.kwa.common.widget.easy_adapter

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface Delegate {

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EasyAdapter.VH

    fun onBindViewHolder(
        item: ListItem,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    )

    fun onViewRecycled(holder: RecyclerView.ViewHolder)

    fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean

    fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder)

    fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder)
}