package com.mixa.dictio.presentation.recycler.differ

import androidx.recyclerview.widget.DiffUtil
import com.mixa.dictio.data.models.DictionaryEntity

class DictionaryDiffItemCallback: DiffUtil.ItemCallback<DictionaryEntity>() {

    override fun areItemsTheSame(oldItem: DictionaryEntity, newItem: DictionaryEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DictionaryEntity, newItem: DictionaryEntity): Boolean {
        return oldItem.language == newItem.language
    }
}