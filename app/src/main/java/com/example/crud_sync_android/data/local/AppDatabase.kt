package com.example.crud_sync_android.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.crud_sync_android.data.local.dao.BarangDao
import com.example.crud_sync_android.data.local.entity.BarangEntity
import com.example.crud_sync_android.data.local.entity.PendingStockMutationEntity

@Database(
    entities = [
        BarangEntity::class,
        PendingStockMutationEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun barangDao(): BarangDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "crud_sync_db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}
