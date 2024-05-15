package com.example.somativaddm.controller.Game

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GameDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(game: Game)

    @Query("Select * From games")
    fun getAll():List<Game>

    @Query("Select * from games where category_name = :genre")
    fun getByGenre(genre:String):List<Game>

    @Query("Select * from games where title = :title")
    fun getByTitle(title:String):List<Game>

    @Query("Select * from games where Platform_name = :platform")
    fun getByPlatform(platform: String):List<Game>

    @Update
    fun update(game:Game):Int
    @Delete
    fun delete(game:Game):Int
}

@Dao
interface PlatformDAO{
    @Insert
    suspend fun insertPlatform(platform: Platform)

    @Query ("Select * from platforms")
    suspend fun getAllPlatforms():List<Platform>

    @Query ("Select * from Platforms where name = :platform")
    suspend fun getByName(platform:String):List<Platform>
}

@Dao
interface CategoryDAO{

    @Insert
    suspend fun insertCategory(category:Category)

    @Query("Select * from categories")
    suspend fun getAllCategories():List<Category>

    @Query ("Select * from categories where name = :name")
    suspend fun getByName(name:String):List<Category>
}
@Dao
interface GamePlatformJoinDao {
    @Insert
    suspend fun insertGamePlatformJoin(join: GamePlatformJoin)

   }

@Dao
interface GameCategoryJoinDao {
    @Insert
    suspend fun insertGameCategoryJoin(join: GameCategoryJoin)

   }