package com.example.vasilyevda

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long? = null, // Опциональное поле id
    val title: String,
    val text: String
)