package com.example.somativaddm.controller.Game

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val gameDao: GameDAO) {

    var games = gameDao.getAll()
    var game:Game? = null

    suspend fun getAllGames():List<Game>{
        return games
    }
    suspend fun getGameByID(ID:Int):Game?{
        games.forEach {
            if(it.id == ID)
                return it
        }
      return null
    }
    suspend fun createGamesDatabase(){
        try{
            val response = RetrofitInstance.gameAPI.getAllGames()
            if(response!=null){
                val json = response.string()
                parseMultipleGamesJson(json)
            }
            else{
                Log.e("API ERROR", "Empty response from API")
            }

        }
        catch (e:Exception){
            Log.e("API ERROR", "Error Fetching Games",e)

        }
    }
    suspend fun addGame(game:Game){
        gameDao.insert(game)
        games = gameDao.getAll()
    }
    suspend fun insertGame(
        id:Int,
        title: String,
        thumb: String?,
        shortDescription: String?,
        gameURL: String?,
        developer: String?,
        releaseDate: String?,
        platformName: String?,
        genreName: String?
    ):Game?{
        var gameExists = false

        //region nullables
        var short_description = shortDescription.isNullOrBlank().let { "Value empty in database" }
        var _thumb = thumb.isNullOrBlank().let { "Value empty in database" }
        var game_URL = gameURL.isNullOrBlank().let { "Value empty in database" }

        var _developer = developer.isNullOrBlank().let { "Value empty in database" }

        var release_Date = releaseDate.isNullOrBlank().let { "Value empty in database" }
        var platform = platformName.isNullOrBlank().let { "Value empty in database" }
        var genre = genreName.isNullOrBlank().let { "Value empty in database" }
        //endregion


        //region check if the game already exist, if does returns null

        games.forEach {
            if(it.id == id) {
                gameExists=true
            }
        }
        if(!gameExists) {


            game = Game(
                id = id,
                title = title,
                thumbURL = _thumb,
                shortDescription = short_description,
                gameURL = game_URL,
                developer = _developer,
                releaseDate = release_Date,
                platform = platform,
                genre = genre
            )

            gameDao.insert(game!!)
            games=gameDao.getAll()

        }else{

        }
        return game
    }

    suspend fun insertGameByTemporary(temporaryGame: TemporaryGame){
        var gameExists = false

        //region nullables
        var thumb = temporaryGame.thumbnail
        if(thumb.isNullOrBlank()) thumb = "No Value"

        var short_description = temporaryGame.short_description
        if(short_description.isNullOrBlank()) short_description = "No Value"

        //endregion


        //region check if the game already exist, if does returns null

        games.forEach {
            if(it.id == temporaryGame.id) {
                gameExists=true
            }
        }
        if(!gameExists) {


            game = Game(
                id = temporaryGame.id!!,
                title = temporaryGame.title!!,
                thumbURL = temporaryGame.thumbnail!!,
                shortDescription = temporaryGame.short_description!!,
                gameURL = temporaryGame.game_url!!,
                developer = temporaryGame.developer!!,
                releaseDate = temporaryGame.release_date!!,
                platform = temporaryGame.platform!!,
                genre = temporaryGame.genre!!
            )

            gameDao.insert(game!!)

            games=gameDao.getAll()

        }else{
        }
    }

    suspend fun parseMultipleGamesJson(json:String){
            try {
                val gameListType = object : TypeToken<List<TemporaryGame>>() {}.type
                val gameList: List<TemporaryGame> = Gson().fromJson(json, gameListType)
                gameList.forEach { insertGameByTemporary(it)}


            }
            catch (e:Exception){
                Log.e("Json Parsin Error", "Error parsing JSON",e)
            }
    }


}
