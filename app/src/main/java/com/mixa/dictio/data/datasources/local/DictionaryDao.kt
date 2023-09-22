package com.mixa.dictio.data.datasources.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mixa.dictio.data.models.entities.DictionaryEntity
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Dao's let us communicate with the database.
 * Check https://developer.android.com/training/data-storage/room for more info.
 */
@Dao
interface DictionaryDao {

    @Query("SELECT * FROM dictionaries")
    abstract fun getAll(): Observable<List<DictionaryEntity>>

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(dictionary: DictionaryEntity): Completable

    @Delete
    abstract fun delete(dictionary: DictionaryEntity): Completable

}