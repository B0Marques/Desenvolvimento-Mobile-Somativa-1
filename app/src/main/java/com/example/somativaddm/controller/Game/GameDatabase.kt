package com.example.somativaddm.controller.Game

import androidx.room.Database
import androidx.room.RoomDatabase

@Database (entities =
    [Game::class],version = 1)
abstract class GameDatabase :RoomDatabase(){
    abstract fun gameDao():GameDAO

}