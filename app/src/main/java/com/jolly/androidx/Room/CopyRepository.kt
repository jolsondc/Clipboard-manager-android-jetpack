package com.jolly.androidx.Room

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CopyRepository internal constructor(private val copyDao: CopyDao) {

    fun getAllData() = copyDao.getAllWords()


     suspend fun insert(data: Word) =withContext(Dispatchers.IO){
            //Log.i("TAG","insert ="+data.data)
            copyDao.insert(data)

    }


    companion object{
        // For Singleton instantiation
        @Volatile private var instance: CopyRepository? = null

        fun getInstance(copyDao: CopyDao) =
                instance ?: synchronized(this) {
                    instance ?: CopyRepository(copyDao).also { instance = it }
                }
    }
    }
