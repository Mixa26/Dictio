package com.mixa.dictio.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mixa.dictio.data.datasources.local.DictionaryDao
import com.mixa.dictio.data.datasources.local.TermDao
import com.mixa.dictio.data.models.entities.DictionaryEntity
import com.mixa.dictio.data.models.entities.TermEntity

/**
 * This is the Room database, we must declare all the entities here and provide
 * Dao's (data access objects). Any time anything is changed in the database
 * (a field in the entity, new entity is added) the version number must be changed,
 * or we can delete the apps data in the phone and not change the number of the
 * version.
 */
@Database(
    entities = [
        DictionaryEntity::class,
        TermEntity::class],
    version = 2,
    exportSchema = false)
abstract class DictioDatabase : RoomDatabase(){
    abstract fun getDictionaryDao(): DictionaryDao
    abstract fun getTermDao(): TermDao
}