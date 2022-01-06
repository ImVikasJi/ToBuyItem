package com.example.tobuy.repository

import com.example.tobuy.db.ItemDatabase
import com.example.tobuy.model.ItemEntity

class ToBuyRepository(private val itemDatabase: ItemDatabase) {

    suspend fun insertItem(itemEntity: ItemEntity) {
        itemDatabase.itemEntityDao().insert(itemEntity)
    }

    suspend fun deleteItem(itemEntity: ItemEntity){
        itemDatabase.itemEntityDao().delete(itemEntity)
    }

    suspend fun getAllItems(): List<ItemEntity>{
      return itemDatabase.itemEntityDao().getAllItemEntities()
    }

}