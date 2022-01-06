package com.example.tobuy.db

import androidx.room.*
import com.example.tobuy.model.ItemEntity

@Dao
interface ItemEntityDao {

    @Query("Select * from item_entity")
    suspend fun getAllItemEntities(): List<ItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemEntity: ItemEntity)

    @Delete
    suspend fun delete(itemEntity: ItemEntity)
}