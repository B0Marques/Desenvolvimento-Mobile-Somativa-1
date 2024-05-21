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

    var gameSelected:Game? = null
    suspend fun getAllGames():List<Game>{
        return games
    }
    suspend fun getGameByID(ID:Int):Game?{
        return gameDao.getGameById(ID)
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

    suspend fun insertGameByTemporary(temporaryGame: TemporaryGame){


        //region nullables
        var thumb = temporaryGame.thumbnail
        if(thumb.isNullOrBlank()) thumb = "No Value"

        var short_description = temporaryGame.short_description
        if(short_description.isNullOrBlank()) short_description = "No Value"

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
    }

    suspend fun parseMultipleGamesJson(json:String){
            try {
                val gameListType = object : TypeToken<List<TemporaryGame>>() {}.type
                val gameList: List<TemporaryGame> = Gson().fromJson(json, gameListType)

                gameList.forEach {
                        if(!gameAlreadyExist(it.id!!))
                            insertGameByTemporary(it)
                    else
                        Log.d("Parse_Debug", "Game ${it.id}, already exist: ${it.title}")
                }


            }
            catch (e:Exception){
                Log.e("Json Parsin Error", "Error parsing JSON",e)
            }
    }

    suspend fun gameAlreadyExist(id:Int):Boolean{
        return gameDao.getGameById(id) != null
    }


}
