package com.hzrelaper.hospitalmachine.nettools.int

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("api/user")
    fun login(@Body enty: UserEntity): Call<LoginResult?>?
}