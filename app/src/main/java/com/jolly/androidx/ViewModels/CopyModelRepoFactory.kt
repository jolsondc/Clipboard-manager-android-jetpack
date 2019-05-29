package com.jolly.androidx.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jolly.androidx.Room.CopyRepository

class CopyModelRepoFactory (private val repo:CopyRepository):ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CopyViewModel(repo) as T
    }
}