package com.mixa.dictio.presentation.view.states

sealed class InsertTermState {
    object Success: InsertTermState()
    data class Error(val messageResourceId: Int): InsertTermState()
}