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

@Entity(tableName = "platforms")
data class Platform(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val name:String
)

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id:Int =0,
    val name:String
)

@Entity(
    tableName = "game_Platform_join",
    primaryKeys = ["gameId","PlatformId"],
    foreignKeys = [
        ForeignKey(entity = Game::class, parentColumns = ["id"], childColumns = ["gameId"]),
        ForeignKey(entity = Platform::class, parentColumns = ["id"], childColumns = ["PlatformId"])
    ]
)
data class GamePlatformJoin(
    val gameId:Int,
    val PlatformId:Int
)

@Entity(
    tableName = "game_category_join",
    primaryKeys = ["gameId","categoryId"],
    foreignKeys = [
        ForeignKey(entity = Game::class, parentColumns = ["id"],childColumns = ["gameId"] ),
        ForeignKey(entity = Category::class, parentColumns = ["id"], childColumns = ["categoryId"])
    ]
)
data class GameCategoryJoin(
    val gameId: Int,
    val categoryId:Int
)