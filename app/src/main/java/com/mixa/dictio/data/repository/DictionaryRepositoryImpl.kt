package com.mixa.dictio.data.repository

import com.mixa.dictio.data.datasources.DictionaryDao
import com.mixa.dictio.data.models.DictionaryEntity
import io.reactivex.Completable
import io.reactivex.Observable

class DictionaryRepositoryImpl(
    private val database: DictionaryDao
): DictionaryRepository {

    override fun getAll(): Observable<List<DictionaryEntity>> {
        return database.getAll()
    }

    override fun insert(dictionary: DictionaryEntity): Completable {
        return database.insert(dictionary)
    }

    override fun delete(dictionary: DictionaryEntity): Completable {
        return database.delete(dictionary)
    }
}