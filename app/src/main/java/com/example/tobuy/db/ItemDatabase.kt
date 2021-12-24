package com.example.tobuy.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tobuy.model.ItemEntity

@Database(entities = [ItemEntity::class], version = 1)
abstract class ItemDatabase: RoomDatabase() {
    abstract fun itemEntityDao(): ItemEntityDao

    companion object {
        @Volatile
        private var instance: ItemDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ItemDatabase::class.java,
                "to-buy-database.db"
            ).build()
    }
}