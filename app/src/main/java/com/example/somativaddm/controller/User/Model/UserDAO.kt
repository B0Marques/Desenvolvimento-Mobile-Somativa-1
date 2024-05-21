package com.example.somativaddm.controller.User.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("Select * FROM users")
    fun getAll(): List<User>

    @Query ("Select * from users where username = :username")
    fun getByName(username:String): List<User>

    @Update
    fun update(user:User):Int


}