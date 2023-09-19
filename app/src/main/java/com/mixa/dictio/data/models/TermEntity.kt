package com.mixa.dictio.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "terms")
data class TermEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    var term: String?,
    var translation: String?,
    var dictionaryId: Int
)