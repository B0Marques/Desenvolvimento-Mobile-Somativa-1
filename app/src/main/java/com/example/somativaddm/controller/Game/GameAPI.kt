package com.example.somativaddm.controller.Game

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object RetrofitInstance{
    private const val BASE_URL = "https://www.freetogame.com/api/"

    val gameAPI: GameAPI by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(GameAPI::class.java)
    }
}
interface GameAPI {
    @GET("games")
    suspend fun getAllGames():Game

    @GET("game")
    suspend fun getGameByID(@Query("id") id: Int):Response<ResponseBody>
}

