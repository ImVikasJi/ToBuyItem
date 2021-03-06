package com.example.tobuy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_entity")
data class ItemEntity (
    @PrimaryKey
    val id: String = "",
    val title: String = "",
    val description: String? = null,
    val priority: Int =0,
    val createdAt: Long = 0L,
    val category: String = ""
)