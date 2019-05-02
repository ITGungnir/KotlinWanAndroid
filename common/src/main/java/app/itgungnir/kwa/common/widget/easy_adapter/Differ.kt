package app.itgungnir.kwa.common.widget.easy_adapter

import android.os.Bundle

interface Differ {

    fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean

    fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean

    fun getChangePayload(oldItem: ListItem, newItem: ListItem): Bundle?
}