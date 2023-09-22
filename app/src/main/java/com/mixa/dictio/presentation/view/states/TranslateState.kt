package com.mixa.dictio.presentation.view.states

import com.mixa.dictio.data.models.responses.TranslateResponse


/**
 * State for retrieving translations.
 */
sealed class TranslateState {
    data class Success(val translation: TranslateResponse): TranslateState()
    data class Error(val messageResourceId: Int): TranslateState()
}