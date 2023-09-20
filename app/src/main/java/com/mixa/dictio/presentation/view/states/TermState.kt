package com.mixa.dictio.presentation.view.states

import com.mixa.dictio.data.models.TermEntity

/**
 * State for retrieving terms.
 */
sealed class TermState {
    data class Success(val terms: List<TermEntity>): TermState()
    data class Error(val messageResourceId: Int): TermState()
}