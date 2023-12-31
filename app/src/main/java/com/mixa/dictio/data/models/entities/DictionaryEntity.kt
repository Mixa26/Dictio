package com.mixa.dictio.data.models.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "dictionaries")

data class DictionaryEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    var language: String,
    var flagImage: String,
    val translationCode: String
)