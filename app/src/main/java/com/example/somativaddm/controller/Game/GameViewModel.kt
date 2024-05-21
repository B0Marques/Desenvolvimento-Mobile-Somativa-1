package com.example.somativaddm.controller.Game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: GameRepository
):ViewModel() {
    val allGamesLiveData = MutableLiveData<List<Game>>()

    fun insertGame(game: Game){
        viewModelScope.launch (Dispatchers.IO){
           repository.addGame(game)
            refresh()
        }

    }
    fun refresh(){
        viewModelScope.launch (Dispatchers.IO){
            //allGamesLiveData.value = repository.getAllGames()

            val games = repository.getAllGames()
            allGamesLiveData.postValue(games)
        }
    }
}