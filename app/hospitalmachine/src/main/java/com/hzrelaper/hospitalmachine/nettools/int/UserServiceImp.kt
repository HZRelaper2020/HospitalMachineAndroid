package com.hzrelaper.hospitalmachine.nettools.int

import com.hzrelaper.hospitalmachine.nettools.ApiClient
import retrofit2.Callback

class UserServiceImp {
    private var service:UserService? = null

    private fun getService(): UserService? {
        return  ApiClient().getUserService()
    }

    fun login(username:String,pwd:String,callback: Callback<LoginResult?>){
        var entity = UserEntity()
        entity.username = username
        entity.password = pwd
        entity.action = "login"
        getService()?.login(entity)?.enqueue(callback)
    }
}