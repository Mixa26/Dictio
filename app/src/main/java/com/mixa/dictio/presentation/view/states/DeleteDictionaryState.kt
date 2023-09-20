package com.mixa.dictio.presentation.view.states

/**
 * State for deleting dictionaries.
 */
sealed class DeleteDictionaryState {
    object Success: DeleteDictionaryState()
    data class Error(val messageResourceId: Int): DeleteDictionaryState()
}