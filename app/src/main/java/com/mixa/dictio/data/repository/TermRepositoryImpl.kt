package com.mixa.dictio.data.repository

import com.mixa.dictio.data.datasources.local.TermDao
import com.mixa.dictio.data.datasources.remote.TranslateService
import com.mixa.dictio.data.models.entities.TermEntity
import com.mixa.dictio.data.models.requests.TranslateRequest
import com.mixa.dictio.data.models.responses.TranslateResponse
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Repositories let us establish communication between the ViewModel and the database.
 */
class TermRepositoryImpl(
    private val database: TermDao,
    private val remoteDataSource: TranslateService
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

    override fun translate(request: TranslateRequest): Observable<TranslateResponse> {
        return remoteDataSource.translate(request)
    }
}