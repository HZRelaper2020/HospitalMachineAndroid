package com.hzrelaper.hospitalmachine.nettools.int

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SuggestionService {
    @POST("api/suggestion")
    fun add(@Body req: SuggestionRequst): Call<SuggestionResult?>
}