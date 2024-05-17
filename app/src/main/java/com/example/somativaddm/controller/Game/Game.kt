package com.example.somativaddm.controller.Game

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.net.URL

@Entity(tableName = "games")
data class Game (
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    @ColumnInfo(index=true)
    var title:String,
    var thumbURL: String,
    var shortDescription:String,
    var gameURL: String,
    var developer:String,
    var releaseDate:String,
    var platform:String,
    var genre:String
)

class TemporaryGame(
    var id:Int? = 0,
    var title:String?,
    var thumbnail: String?,
    var short_description:String?,
    var game_url: String?,
    var developer:String?,
    var release_date:String?,
    var platform:String?,
    var genre:String?
)
