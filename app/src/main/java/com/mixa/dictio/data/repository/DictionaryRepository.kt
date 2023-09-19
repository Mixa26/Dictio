package com.mixa.dictio.data.repository

import com.mixa.dictio.data.models.DictionaryEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface DictionaryRepository {

    fun getAll(): Observable<List<DictionaryEntity>>
    fun insert(dictionary: DictionaryEntity): Completable
    fun delete(dictionary: DictionaryEntity): Completable
}