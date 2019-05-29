package com.jolly.androidx.Utilities

import android.content.Context
import com.jolly.androidx.Room.CopyRepository
import com.jolly.androidx.Room.CopyRoomDatabase
import com.jolly.androidx.ViewModels.CopyModelRepoFactory

object InjectUtil {


    fun provideAllDataViewModelFactory(context: Context):CopyModelRepoFactory{
        val repo= getCopyModelRespository(context)
        return CopyModelRepoFactory(repo)
    }

    private fun getCopyModelRespository(context: Context): CopyRepository {
        return CopyRepository.getInstance(CopyRoomDatabase.getInstance(context).copyDao())
    }

}