package com.mixa.dictio.data.repository

import com.mixa.dictio.data.models.TermEntity
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Repositories let us establish communication between the ViewModel and the database.
 */
interface TermRepository {

    fun getAllByDictId(dictionaryId: Int): Observable<List<TermEntity>>
    fun insert(term: TermEntity): Completable
    fun delete(term: TermEntity): Completable
}