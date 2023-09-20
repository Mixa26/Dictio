package com.mixa.dictio.presentation.contract

import androidx.lifecycle.LiveData
import com.mixa.dictio.data.models.DictionaryEntity
import com.mixa.dictio.data.models.TermEntity
import com.mixa.dictio.presentation.view.states.DeleteDictionaryState
import com.mixa.dictio.presentation.view.states.DeleteTermState
import com.mixa.dictio.presentation.view.states.DictionaryState
import com.mixa.dictio.presentation.view.states.InsertDictionaryState
import com.mixa.dictio.presentation.view.states.InsertTermState
import com.mixa.dictio.presentation.view.states.TermState

/**
 * We define interfaces for the ViewModels in the MainContract.
 */
interface MainContract {
    interface DictionaryViewModel{

        val dictionaryState: LiveData<DictionaryState>
        val insertDictionaryState: LiveData<InsertDictionaryState>
        val deleteDictionaryState: LiveData<DeleteDictionaryState>

        fun getAll()
        fun insert(dictionary: DictionaryEntity)
        fun delete(dictionary: DictionaryEntity)
    }

    interface TermViewModel{

        val termState: LiveData<TermState>
        val insertTermState: LiveData<InsertTermState>
        val deleteTermState: LiveData<DeleteTermState>

        fun getAllByDictId(dictionaryId: Int)
        fun insert(term: TermEntity)
        fun delete(term: TermEntity)
    }
}