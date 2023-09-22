package com.mixa.dictio.presentation.recycler.differ

import androidx.recyclerview.widget.DiffUtil
import com.mixa.dictio.data.models.entities.DictionaryEntity

/**
 * This class is needed by the recycler view for data representation.
 */
class DictionaryDiffItemCallback: DiffUtil.ItemCallback<DictionaryEntity>() {

    override fun areItemsTheSame(oldItem: DictionaryEntity, newItem: DictionaryEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DictionaryEntity, newItem: DictionaryEntity): Boolean {
        return oldItem.language == newItem.language
    }
}