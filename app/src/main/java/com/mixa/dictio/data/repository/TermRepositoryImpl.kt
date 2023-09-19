package com.mixa.dictio.data.repository

import com.mixa.dictio.data.datasources.TermDao
import com.mixa.dictio.data.models.TermEntity
import io.reactivex.Completable
import io.reactivex.Observable

class TermRepositoryImpl(
    private val database: TermDao
): TermRepository {

    override fun getAllByDictId(dictionaryId: Int): Observable<List<TermEntity>> {
        return database.getAllByDictId(dictionaryId)
    }

    override fun insert(term: TermEntity): Completable {
        return database.insert(term)
    }

    override fun delete(term: TermEntity): Completable {
        return database.delete(term)
    }
}