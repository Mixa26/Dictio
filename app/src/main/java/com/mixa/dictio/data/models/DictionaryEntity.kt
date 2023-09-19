package com.mixa.dictio.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionaries")
data class DictionaryEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    var language: String
)