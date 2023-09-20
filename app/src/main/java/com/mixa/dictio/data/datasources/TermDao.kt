package com.mixa.dictio.data.datasources

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mixa.dictio.data.models.TermEntity
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Dao's let us communicate with the database.
 * Check https://developer.android.com/training/data-storage/room for more info.
 */
@Dao
interface TermDao {

    @Query("SELECT * FROM terms WHERE dictionaryId == :dictionaryId")
    abstract fun getAllByDictId(dictionaryId: Int): Observable<List<TermEntity>>

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(term: TermEntity): Completable

    @Delete
    abstract fun delete(term: TermEntity): Completable
}