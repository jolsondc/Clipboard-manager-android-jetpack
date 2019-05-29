package com.jolly.androidx.ViewModels

import androidx.lifecycle.ViewModel
import com.jolly.androidx.Room.CopyRepository

class CopyViewModel internal constructor(copyRepository: CopyRepository): ViewModel() {


       val allData = copyRepository.getAllData()


}