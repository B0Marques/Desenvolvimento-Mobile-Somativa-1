package com.example.somativaddm.controller.User

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.somativaddm.controller.User.Repository.UserDatabase
import com.example.somativaddm.controller.User.Repository.UserRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserService(private val db: UserDatabase, context: Context):ViewModel() {

    private val repository:UserRepository = UserDatabase.getInstance(context).UserRepository()
    fun login(username:String, password:String){
        viewModelScope.launch (Dispatchers.IO){

        }

    }
    fun insertUser(username:String, password:String){
        viewModelScope.launch (Dispatchers.IO){
            val userToInsert = UserDTO(id = 1,nickname = username, password = password)
            repository.insertUser(userToInsert)
            Log.d("DEBUG","user inserted")

        }

    }

    suspend fun findByUserName(username:String):UserDTO?{
        var userToReturn:UserDTO? = null
        val deferred = CompletableDeferred<UserDTO?>()
        viewModelScope.launch (Dispatchers.IO){
            userToReturn = repository.findByUserName(username)
            if(userToReturn!=null){
                Log.d("DEBUG", "Find User, ${userToReturn!!.nickname}")
                deferred.complete(userToReturn)
            }
            else{
                deferred.complete(null)
            }
        }
        return deferred.await()
    }


}