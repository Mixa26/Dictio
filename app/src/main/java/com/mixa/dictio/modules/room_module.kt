package com.mixa.dictio.modules

import androidx.room.Room
import com.mixa.dictio.data.database.DictioDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    single{ Room.databaseBuilder(androidContext(), DictioDatabase::class.java, "DictioDatabase")
        .fallbackToDestructiveMigration()
        .build()}
}