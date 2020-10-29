package com.hzrelaper.hospitalmachine.nettools.int

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AnswerService {
    @POST("api/answer")
    fun getAnswersByQuestionId(@Body enty: AnswerEntityRequest): Call<AnswerResult?>
    @POST("api/answer")
    fun addAnswer(@Body enty: AnswerEntity): Call<AnswerResult?>
}