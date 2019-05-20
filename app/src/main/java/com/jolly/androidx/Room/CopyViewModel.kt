package com.jolly.androidx.Room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CopyViewModel(application: Application): AndroidViewModel(application) {
    private  val repository: CopyRepository

      val allData: LiveData<List<Word>>
    init {
        val copyDao = CopyRoomDatabase.getDatabase(application,viewModelScope).copyDao()
        repository = CopyRepository(copyDao)
        allData = repository.allWords
    }

    fun insert(data: Word) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(data)
    }

}