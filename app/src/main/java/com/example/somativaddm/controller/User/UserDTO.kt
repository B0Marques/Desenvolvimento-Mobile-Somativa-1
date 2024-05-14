package com.example.somativaddm.controller.User

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity
data class UserDTO(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    @ColumnInfo(name = "user_name")
    var nickname:String,
    @ColumnInfo(name = "user_password")
    var password:String):Serializable