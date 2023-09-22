package com.mixa.dictio.presentation.view.states

/**
 * State for inserting dictionaries.
 */
sealed class InsertDictionaryState {
    object Success: InsertDictionaryState()
    data class Error(val messageResourceId: Int): InsertDictionaryState()
}