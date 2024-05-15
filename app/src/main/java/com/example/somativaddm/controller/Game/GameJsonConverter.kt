package com.example.somativaddm.controller.Game

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import java.io.IOException

class GameJsonConverter {
/*
    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Game::class.java, GameDeserializer())
        .create()

    // Função para converter o JSON em um objeto Game
    fun jsonToGame(json: String): Game {
        return gson.fromJson(json, Game::class.java)
    }


    private abstract class GameDeserializer :TypeAdapter<Game>(){
        @Throws(IOException::class)
        override fun read(reader : JsonReader): Game {
             var id = -1
            var title = " "
            var thumbURL = " "
            var shortDescription = " "
            var gameURL = " "
            var developer = ""
            var releaseDate = ""
            var platform = Platform(-1," ")
            var genre = Category(-1," ")

            reader.beginObject()
            while(reader.hasNext()){
                when (reader.nextName()){
                    "id" -> id = reader.nextInt()
                    "title" -> title = reader.nextString()
                    "thumb" -> thumbURL =reader.nextString()
                    "shortDescription" -> shortDescription = reader.nextString()
                    "gameURL" -> gameURL = reader.nextString()
                    "developer" -> developer = reader.nextString()
                    "releaseDate" -> releaseDate = reader.nextString()
                    "platform" -> platform = Platform(-1,reader.nextString())
                    "genre" -> genre = Category(-1,reader.nextString())
                    else -> reader.skipValue()
                }
            }
            reader.endObject()

            return Game(id,title,thumbURL,shortDescription,gameURL,developer,releaseDate,platform.name,genre.name)
        }
    }
}

 */
}