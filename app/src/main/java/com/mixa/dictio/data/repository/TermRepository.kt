package com.mixa.dictio.data.repository

import com.mixa.dictio.data.models.entities.TermEntity
import com.mixa.dictio.data.models.requests.TranslateRequest
import com.mixa.dictio.data.models.responses.TranslateResponse
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Repositories let us establish communication between the ViewModel and the database.
 */
interface TermRepository {

    fun getAllByDictId(dictionaryId: Int): Observable<List<TermEntity>>
    fun insert(term: TermEntity): Completable
    fun delete(term: TermEntity): Completable

    fun translate(request: TranslateRequest): Observable<TranslateResponse>
}