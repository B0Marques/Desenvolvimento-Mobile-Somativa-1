package com.example.somativaddm.model

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FreeToGamesClient {
    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://www.freetogame.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        val api = retrofit.create(FreeToGameAPI::class.java)

        suspend fun getFreeGames(id: Int, category: String, platform: String, sortBy: String) =
            api.getFreeGames(id, platform, category, sortBy)


    }
}