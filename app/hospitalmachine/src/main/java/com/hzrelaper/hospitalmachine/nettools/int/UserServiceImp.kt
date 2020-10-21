package com.hzrelaper.hospitalmachine.nettools.int

import com.hzrelaper.hospitalmachine.data.entity.UserEntity
import com.hzrelaper.hospitalmachine.nettools.ApiClient
import retrofit2.Callback

class UserServiceImp {
    private var service:UserService? = null

    private fun getService(): UserService? {
        if (service == null)
            service = ApiClient().getUserService()
        return service
    }

    fun login(username:String,pwd:String,callback: Callback<UserEntity?>){
        val service = getService()
        var entity = UserEntity()
        entity.username = username
        entity.password = pwd
        entity.action = "login"
        service?.login(entity)?.enqueue(callback)
    }
}