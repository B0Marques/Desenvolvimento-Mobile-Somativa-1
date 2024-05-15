package com.example.somativaddm.controller.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.somativaddm.controller.User.Model.User
import com.example.somativaddm.controller.User.Model.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UserRepository):ViewModel(){
    val usersLiveData = MutableLiveData<List<User>>()

    fun add(user: User){
        repository.add(user)
        usersLiveData.value = repository.users
    }

    fun update(user: User){
        repository.update(user)
        usersLiveData.value = repository.users
    }
    fun refresh(){
        usersLiveData.value = repository.users
    }
}