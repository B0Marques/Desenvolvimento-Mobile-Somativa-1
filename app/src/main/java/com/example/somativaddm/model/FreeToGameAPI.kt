package com.example.somativaddm.model

import android.telecom.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FreeToGameAPI {
    @GET("/api/games")
    suspend fun getFreeGames(): List<Game>

    @GET("/api/games")
    suspend fun getFreeGames(
        @Query("id") gameId: Int? = null,
        @Query("category") category: String? = null,
        @Query("platform") platform: String? = null,
        @Query("sort-by") sortBy: String? = null,
    ): List<Game>

}