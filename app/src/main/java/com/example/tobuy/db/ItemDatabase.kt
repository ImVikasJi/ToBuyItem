package com.example.tobuy.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tobuy.model.CategoryEntity
import com.example.tobuy.model.ItemEntity

@Database(entities = [ItemEntity::class,CategoryEntity::class], version = 2)
abstract class ItemDatabase: RoomDatabase() {
    abstract fun itemEntityDao(): ItemEntityDao
    abstract fun categoryEntityDao() : CategoryEntityDao

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
            )
                .addMigrations(MIGRATION_1_2())
                .build()
    }

    // to migrate from a version to other
    class MIGRATION_1_2 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `category_entity` (`id` TEXT NOT NULL, `name` TEXT NOT NULL,PRIMARY KEY(`id`))")
        }
    }

}