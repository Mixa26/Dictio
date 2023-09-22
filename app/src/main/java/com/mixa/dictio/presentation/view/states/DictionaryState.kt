package com.mixa.dictio.presentation.view.states

import com.mixa.dictio.data.models.entities.DictionaryEntity

/**
 * State for retrieving dictionaries.
 */
sealed class DictionaryState {
    data class Success(val dictionaries: List<DictionaryEntity>): DictionaryState()
    data class Error(val messageResourceId: Int): DictionaryState()
}