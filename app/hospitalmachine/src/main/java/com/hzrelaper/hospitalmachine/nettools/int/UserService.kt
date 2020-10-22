package com.hzrelaper.hospitalmachine.nettools.int

import com.hzrelaper.hospitalmachine.data.entity.LoginResult
import com.hzrelaper.hospitalmachine.data.entity.Repo
import com.hzrelaper.hospitalmachine.data.entity.UserEntity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*


interface UserService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Call<List<Repo?>?>?

    @POST("api/user")
    fun login(@Body  enty:UserEntity):Call<LoginResult?>?
}