package com.mixa.dictio.data.repository

import com.mixa.dictio.data.models.TermEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface TermRepository {

    fun getAllByDictId(dictionaryId: Int): Observable<List<TermEntity>>
    fun insert(term: TermEntity): Completable
    fun delete(term: TermEntity): Completable
}