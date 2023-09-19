package com.mixa.dictio.presentation.view.states

sealed class DeleteTermState {
    object Success: DeleteTermState()
    data class Error(val messageResourceId: Int): DeleteTermState()
}