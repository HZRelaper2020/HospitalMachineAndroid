package com.hzrelaper.hospitalmachine.nettools.int

import com.hzrelaper.hospitalmachine.data.entity.Repo
import com.hzrelaper.hospitalmachine.data.entity.UserEntity
import com.hzrelaper.hospitalmachine.ui.login.LoginResult
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*


interface UserService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Call<List<Repo?>?>?

    @POST("user")
    fun login(@Body  enty:UserEntity):Call<UserEntity?>?
}