package com.vinio.firstlab

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vinio.firstlab.entity.CharacterDao
import com.vinio.firstlab.entity.CharacterEntity
import com.vinio.firstlab.entity.ListConverter
import android.content.Context
import androidx.room.Room

@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    object DatabaseProvider {
        private var dbInstance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (dbInstance != null) {
                return dbInstance as AppDatabase
            } else {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "game_of_thrones.db"
                ).build()
                dbInstance = instance
                return instance
            }
        }
    }
}

