package com.mixa.dictio.data.repository

import com.mixa.dictio.data.datasources.local.DictionaryDao
import com.mixa.dictio.data.models.entities.DictionaryEntity
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Repositories let us establish communication between the ViewModel and the database.
 */
class DictionaryRepositoryImpl(
    private val database: DictionaryDao
): DictionaryRepository {

    override fun getAll(): Observable<List<DictionaryEntity>> {
        return database.getAll()
    }

    override fun insert(dictionary: DictionaryEntity): Completable {
        return database.insert(dictionary)
    }

    override fun insertAll(dictionary: List<DictionaryEntity>): Completable {
        return database.insertAll(dictionary)
    }

    override fun delete(dictionary: DictionaryEntity): Completable {
        return database.delete(dictionary)
    }
}