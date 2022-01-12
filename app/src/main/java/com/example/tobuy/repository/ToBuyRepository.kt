package com.example.tobuy.repository

import com.example.tobuy.db.ItemDatabase
import com.example.tobuy.model.CategoryEntity
import com.example.tobuy.model.ItemEntity
import kotlinx.coroutines.flow.Flow

class ToBuyRepository(private val itemDatabase: ItemDatabase) {

    // region ItemEntity
    suspend fun insertItem(itemEntity: ItemEntity) {
        itemDatabase.itemEntityDao().insert(itemEntity)
    }

    suspend fun deleteItem(itemEntity: ItemEntity) {
        itemDatabase.itemEntityDao().delete(itemEntity)
    }

    suspend fun updateItem(itemEntity: ItemEntity){
        itemDatabase.itemEntityDao().update(itemEntity)
    }

    fun getAllItems(): Flow<List<ItemEntity>> {
        return itemDatabase.itemEntityDao().getAllItemEntities()
    }
    // end region ItemEntity


    //region CategoryEntity
    suspend fun insertCategory(categoryEntity: CategoryEntity) {
        itemDatabase.categoryEntityDao().insert( categoryEntity)
    }

    suspend fun deleteCategory(categoryEntity: CategoryEntity) {
        itemDatabase.categoryEntityDao().delete(categoryEntity)
    }

    suspend fun updateCategory(categoryEntity: CategoryEntity){
        itemDatabase.categoryEntityDao().update(categoryEntity)
    }

    fun getAllCategories(): Flow<List<CategoryEntity>> {
        return itemDatabase.categoryEntityDao().getAllCategoriesEntities()
    }

    //end region CategoryEntity

}