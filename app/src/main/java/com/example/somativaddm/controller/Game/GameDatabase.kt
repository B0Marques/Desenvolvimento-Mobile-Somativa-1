package com.example.somativaddm.controller.Game

import androidx.room.Database
import androidx.room.RoomDatabase

@Database (entities =
    [Game::class,
    Platform::class,
    Category::class,
    GamePlatformJoin::class,
    GameCategoryJoin::class],version = 1)
abstract class GameDatabase :RoomDatabase(){
    abstract fun gameDao():GameDAO
    abstract fun platformDao():PlatformDAO
    abstract fun categoryDao():CategoryDAO

    abstract fun gamePlatformJoinDao():GamePlatformJoinDao
    abstract fun gameCategoryJoinDao():GameCategoryJoinDao

}