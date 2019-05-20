package com.jolly.androidx.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [Word::class], version = 1)
abstract class CopyRoomDatabase : RoomDatabase() {

    abstract fun copyDao(): CopyDao


    companion object {
        @Volatile
        private var INSTANCE: CopyRoomDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope): CopyRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        CopyRoomDatabase::class.java,
                        "copy_database"
                ).addCallback(CopyDatabaseCallback(scope)).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    class CopyDatabaseCallback (
            private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.copyDao())
                }
            }
        }

        suspend fun populateDatabase(copyDao: CopyDao) {
            copyDao.deleteAll()


            copyDao.insert(Word(0,"Hello"))
            copyDao.insert(Word(0,"World!"))
        }
    }
}