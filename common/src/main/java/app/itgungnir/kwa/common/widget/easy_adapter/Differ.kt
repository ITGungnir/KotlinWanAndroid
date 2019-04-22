package app.itgungnir.kwa.common.widget.easy_adapter

import android.os.Bundle

interface Differ<T : ListItem> {

    fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    fun areContentsTheSame(oldItem: T, newItem: T): Boolean

    fun getChangePayload(oldItem: T, newItem: T): Bundle?
}