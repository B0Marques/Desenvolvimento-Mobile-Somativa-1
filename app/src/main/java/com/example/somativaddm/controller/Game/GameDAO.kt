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

    @Query("Select * from games where genre = :genre")
    fun getByGenre(genre:String):List<Game>

    @Query("Select * from games where id = :gameId LIMIT 1")
    fun getGameById(gameId:Int):Game?

    @Query(
        "Select * from games ORDER BY title ASC"
    )
    suspend fun getAllGamesOrderedByTitle():List<Game>

    @Query("Select * from games where title = :title")
    fun getByTitle(title:String):List<Game>

    @Query("Select * from games where platform = :platform")
    fun getByPlatform(platform: String):List<Game>

    @Update
    fun update(game:Game):Int
    @Delete
    fun delete(game:Game):Int
}
