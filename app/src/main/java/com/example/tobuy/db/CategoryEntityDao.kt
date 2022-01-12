package com.example.tobuy.db

import androidx.room.*
import com.example.tobuy.model.CategoryEntity
import com.example.tobuy.model.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryEntityDao {
    @Query("Select * from category_entity")
    fun getAllCategoriesEntities(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categoryEntity: CategoryEntity)

    @Delete
    suspend fun delete(categoryEntity: CategoryEntity)

    @Update
    suspend fun update(categoryEntity: CategoryEntity)
}
