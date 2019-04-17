package app.itgungnir.kwa.common.widget.recycler_list

import android.os.Bundle

interface DiffAnalyzer<T> {

    fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    fun areContentsTheSame(oldItem: T, newItem: T): Boolean

    fun getChangePayload(oldItem: T, newItem: T): Bundle?
}