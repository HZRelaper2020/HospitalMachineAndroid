package com.hzrelaper.hospitalmachine.nettools

import com.google.gson.GsonBuilder
import com.hzrelaper.hospitalmachine.nettools.int.UserService
import com.hzrelaper.hospitalmachine.url.StaticUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {
    private var retrofit: Retrofit? = null

    private fun getClient(): Retrofit? {
        if (retrofit == null) {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            retrofit = Retrofit.Builder()
                .baseUrl(StaticUrl.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit
    }

    fun getUserService(): UserService? {
        val service = getClient()?.create(UserService::class.java)
        return service
    }

}