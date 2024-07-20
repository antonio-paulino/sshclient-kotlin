package com.paulinoo.sshclient.manager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paulinoo.sshclient.manager.database.Converters
import com.paulinoo.sshclient.manager.database.SSHClientManager
import com.paulinoo.sshclient.manager.database.SSHClientManagerDAO

@Database(entities = [SSHClientManager::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sshClientManagerDAO(): SSHClientManagerDAO

    companion object {
        const val DATABASE_NAME = "sshclient-db"
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context:Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}