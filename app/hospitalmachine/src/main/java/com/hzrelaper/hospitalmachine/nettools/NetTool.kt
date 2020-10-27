package com.hzrelaper.hospitalmachine.nettools

import com.hzrelaper.hospitalmachine.nettools.int.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetTool {
//    fun  aaaa() {
//
//        val BASE_URL = "https://api.github.com/"
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val service = retrofit.create(UserService::class.java)
//        val mycallback= object : Callback<List<Repo?>?> {
//            override fun onResponse(call: Call<List<Repo?>?>, response: Response<List<Repo?>?>) {
//                var msg = response.message()
//                var list = response.body();
//
//            }
//
//            override fun onFailure(call: Call<List<Repo?>?>, t: Throwable) {
//
//            }
//        }
//        service.listRepos("octocat")?.enqueue(mycallback)
//    }

}