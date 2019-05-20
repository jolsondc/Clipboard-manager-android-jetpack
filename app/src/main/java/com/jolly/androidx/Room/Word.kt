package com.jolly.androidx.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "copy_table")
class Word(@PrimaryKey(autoGenerate = true)val id:Int, val data:String?)