package com.mixa.dictio.presentation.view.states

import com.mixa.dictio.data.models.DictionaryEntity

sealed class DictionaryState {
    data class Success(val dictionaries: List<DictionaryEntity>): DictionaryState()
    data class Error(val messageResourceId: Int): DictionaryState()
}