package com.example.somativaddm.controller.User.Model

import javax.inject.Inject

class UserRepository @Inject constructor(var dao:UserDAO){

    var users = dao.getAll()

    fun add(user:User){
        dao.insert(user)
        users = dao.getAll()

    }

    fun update(user:User){
        dao.update(user)
        users=dao.getAll()

    }
}