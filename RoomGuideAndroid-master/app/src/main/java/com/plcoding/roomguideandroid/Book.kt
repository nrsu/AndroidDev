package com.plcoding.roomguideandroid

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    val title: String,
    val description: String,
    val price: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
