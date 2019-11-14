package dev.joshhalvorson.jobapptracker.util

import androidx.recyclerview.widget.DiffUtil
import dev.joshhalvorson.jobapptracker.model.Application

class ApplicationsDiffCallback(
    private val oldList: List<Application>,
    private val newList: List<Application>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].company === newList[newItemPosition].company
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }

}