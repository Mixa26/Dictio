package com.mixa.dictio.data.repository

import com.mixa.dictio.data.models.entities.DictionaryEntity
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Repositories let us establish communication between the ViewModel and the database.
 */
interface DictionaryRepository {

    fun getAll(): Observable<List<DictionaryEntity>>
    fun insert(dictionary: DictionaryEntity): Completable
    fun delete(dictionary: DictionaryEntity): Completable
}