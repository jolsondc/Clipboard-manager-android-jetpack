package com.jolly.androidx.Room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class CopyRepository(private val copyDao: CopyDao) {

    val allWords: LiveData<List<Word>> = copyDao.getAllWords()

    @WorkerThread
    suspend fun insert(data: Word) {
        copyDao.insert(data)
    }
}