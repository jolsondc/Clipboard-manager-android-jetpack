package com.jolly.androidx.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [Word::class], version = 1,exportSchema = false)
abstract class CopyRoomDatabase : RoomDatabase() {

    abstract fun copyDao(): CopyDao


    companion object {
        @Volatile
        private var INSTANCE: CopyRoomDatabase? = null

        fun getInstance(context: Context):CopyRoomDatabase{
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: getDatabase(context).also { INSTANCE = it }
            }
        }

      internal fun getDatabase(context: Context): CopyRoomDatabase {

            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        CopyRoomDatabase::class.java,
                        "copy_database"
                ).addCallback(object:RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                }).build()
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
                    //populateDatabase(database.copyDao())
                }
            }
        }

    }
}