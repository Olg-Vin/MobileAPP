package com.vinio.firstlab.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String?,
    val culture: String?,
    val born: String?,
    @TypeConverters(ListConverter::class)
    val titles: List<String>?,
    @TypeConverters(ListConverter::class)
    val aliases: List<String>?,
    @TypeConverters(ListConverter::class)
    val playedBy: List<String>?
)
