package com.example.somativaddm.controller.User.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo(index = true)
    var userName:String,
    var password:String,

)