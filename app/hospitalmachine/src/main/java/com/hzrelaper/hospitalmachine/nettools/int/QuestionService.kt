package com.hzrelaper.hospitalmachine.nettools.int

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface QuestionService {
    @POST("api/question")
    fun getlist(@Body enty: QuestionEntityRequest): Call<QuestionResult?>
    @POST("api/question")
    fun addQuestion(@Body enty: QuestionAddRequest): Call<QuestionResult?>
    @POST("api/question")
    fun getSingle(@Body enty: QuestionEntityRequest): Call<QuestionSingleResult?>
    @POST("api/question")
    fun closeQuestion(@Body enty: QuestionEntityRequest): Call<QuestionSingleResult?>
    @POST("api/question")
    fun applyThisAnswer(@Body enty: QuestionEntityRequest): Call<QuestionSingleResult?>
}