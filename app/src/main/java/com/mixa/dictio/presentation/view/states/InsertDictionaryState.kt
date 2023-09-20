package com.mixa.dictio.presentation.view.states

import com.mixa.dictio.data.models.DictionaryEntity

/**
 * State for inserting dictionaries.
 */
sealed class InsertDictionaryState {
    object Success: InsertDictionaryState()
    data class Error(val messageResourceId: Int): InsertDictionaryState()
}