package com.mixa.dictio.presentation.view.states

/**
 * State for deleting terms.
 */
sealed class DeleteTermState {
    object Success: DeleteTermState()
    data class Error(val messageResourceId: Int): DeleteTermState()
}