package com.mixa.dictio.presentation.view.states

/**
 * State for inserting terms.
 */
sealed class InsertTermState {
    object Success: InsertTermState()
    data class Error(val messageResourceId: Int): InsertTermState()
}