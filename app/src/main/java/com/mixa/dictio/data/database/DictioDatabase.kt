package com.mixa.dictio.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mixa.dictio.data.datasources.DictionaryDao
import com.mixa.dictio.data.datasources.TermDao
import com.mixa.dictio.data.models.DictionaryEntity
import com.mixa.dictio.data.models.TermEntity

@Database(
    entities = [
        DictionaryEntity::class,
        TermEntity::class],
    version = 1,
    exportSchema = false)
abstract class DictioDatabase : RoomDatabase(){
    abstract fun getDictionaryDao(): DictionaryDao
    abstract fun getTermDao(): TermDao
}