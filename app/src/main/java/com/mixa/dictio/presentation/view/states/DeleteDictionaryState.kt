package com.mixa.dictio.presentation.view.states

sealed class DeleteDictionaryState {
    object Success: DeleteDictionaryState()
    data class Error(val messageResourceId: Int): DeleteDictionaryState()
}