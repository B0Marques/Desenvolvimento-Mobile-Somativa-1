package com.example.somativaddm.controller.Game

import android.util.Log
import org.json.JSONArray
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val gameDao: GameDAO,
    private val PlatformDao:PlatformDAO,
    private val categoryDAO: CategoryDAO,
    private val gamePlatformJoinDAO: GamePlatformJoinDao,
    private val gameCategoryJoinDAO: GameCategoryJoinDao) {

    //region Games
    var games = gameDao.getAll()
    var game:Game? = null

    suspend fun getAllGames():List<Game>{
        return gameDao.getAll()
    }
    suspend fun getGameByID(ID:Int){
        //extractGameValueFromJson(RetrofitInstance.gameAPI.getGameByID(ID))
        try{
            val response = RetrofitInstance.gameAPI.getGameByID(ID)
            if(response.isSuccessful){
                val json = response.body()?.string()

                if (json!=null) {
                    Log.d("JSON DEBUG",json)
                    extractGameValueFromJson(json)
                }
            }
        }
        catch (e:Exception){

        }
    }
    suspend fun addGame(game:Game){
        gameDao.insert(game)
        games = gameDao.getAll()
    }
    suspend fun insertGame(
        id:Int,
        title: String,
        thumb: String,
        shortDescription: String,
        gameURL: String,
        developer: String,
        releaseDate: String,
        platformName: String,
        genreName: String
    ):Game?{
        var gameExists = false
        //region check if the game already exist, if does returns null

        games.forEach {
            if(it.id == id) {
                gameExists=true
            }
        }
        //endregion
        if(!gameExists) {

            //region check if genre exists if doenst, create a new
            val genres = getAllCategories()
            var genreToGame: Category? = null
            genres.forEach {
                if (it.name == genreName) {
                    genreToGame = it
                }
            }
            if (genreToGame == null) {
                insertCategory(
                    Category(
                        getAllCategories().size + 1,
                        genreName
                    )
                )
            }
            //endregion

            //region check if platform exists, if doesnt, create a new
            val platforms = getAllPlatforms()
            var platformToGame: Platform? = null
            platforms.forEach {
                if (it.name == platformName)
                    platformToGame = it
            }
            if (platformToGame == null) {
                insertPlatform(
                    Platform(
                        getAllPlatforms().size + 1,
                        platformName
                    )
                )
            }
            //endregion

            game = Game(
                id,
                title,
                thumb,
                shortDescription,
                gameURL,
                developer,
                releaseDate,
                getPlatformByName(platformName)!!,
                getCategoryByName(genreName)!!
            )

            gameDao.insert(game!!)
            games=gameDao.getAll()

        }else{
            Log.d("GameDebug","Game Alreay Exist")
        }
        return game
    }
    //endregion

    //region Platform
    suspend fun getAllPlatforms():List<Platform>{
        return PlatformDao.getAllPlatforms()
    }

    suspend fun insertPlatform(platform: Platform){
        if(getPlatformByName(platform.name)==null){
            PlatformDao.insertPlatform(platform)
        }
        else{
            Log.d("PlatformDebug","Platform ${platform.name} already exists")
        }

    }
    suspend fun getPlatformByName(name: String): Platform? {
        val plat = PlatformDao.getByName(name)
        plat.forEach {
            if(it.name == name)
                return it

        }
        return null
    }

    //endregion

    //region Category or Genre

    suspend fun getAllCategories():List<Category>{
        return categoryDAO.getAllCategories()
    }

    suspend fun getCategoryByName(name:String): Category? {
        val genre = categoryDAO.getByName(name)
        genre.forEach {
            if(it.name == name)
                return it

        }
        return null
    }

    suspend fun insertCategory(category: Category){
        if(getCategoryByName(category.name) == null ){
            categoryDAO.insertCategory(category)
        }
        else{
            Log.d("CategoryDEBUG","Category ${category.name} already exists")
        }
    }

    //endregion

    //region Joins
    suspend fun insertGamePlatformJoin(join: GamePlatformJoin){
        gamePlatformJoinDAO.insertGamePlatformJoin(join)
    }

    suspend fun insertGameCategoryJoin(join:GameCategoryJoin){
        gameCategoryJoinDAO.insertGameCategoryJoin(join)
    }

    //endregion

    suspend fun extractGameValueFromJson(json:String):Game{
        val gameValues = mutableListOf<String>()
        val gameTest:Game? = Game(
            title = "Debug Game",
            thumbURL = "game.png",
            shortDescription = "Game only to debug",
            gameURL = "game.url.com",
            developer = "Vapor Company",
            releaseDate = "11/04/2024",
            Platform = getPlatformByName("PC")!!,
            genre = getCategoryByName("DebugGenre")!!
        )
        try{
            val jsonArray = JSONArray(json)
            for(i in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getInt("id")

                gameTest!!.id = id
            }
        }
        catch(e:Exception){
            Log.w("EXCEPTION DEBUG", e.message!!)
        }
        return gameTest!!
    }


}