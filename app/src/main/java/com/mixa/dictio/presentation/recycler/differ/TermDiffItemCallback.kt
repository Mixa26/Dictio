package com.mixa.dictio.presentation.recycler.differ

import androidx.recyclerview.widget.DiffUtil
import com.mixa.dictio.data.models.entities.TermEntity

class TermDiffItemCallback: DiffUtil.ItemCallback<TermEntity>() {
    override fun areContentsTheSame(oldItem: TermEntity, newItem: TermEntity): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: TermEntity, newItem: TermEntity): Boolean {
        return oldItem.term == newItem.term &&
                oldItem.translation == newItem.translation &&
                oldItem.dictionaryId == newItem.dictionaryId
    }
}