package com.example.somativaddm.controller.User.Repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.somativaddm.controller.User.UserDTO
import java.io.Serializable

@Dao
interface UserRepository {
    @Query("SELECT * FROM UserDTO")
    fun getAll(): List<UserDTO>

    @Query("SELECT * FROM userdto WHERE user_name LIKE :name LIMIT 1")
    fun findByUserName(name:String):UserDTO?

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userDTO: UserDTO)

    @Query("SELECT COUNT (*) FROM UserDTO")
    suspend fun countUsers():Long

}