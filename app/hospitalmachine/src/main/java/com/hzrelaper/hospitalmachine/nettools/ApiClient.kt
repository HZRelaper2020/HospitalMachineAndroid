package com.hzrelaper.hospitalmachine.nettools

import com.google.gson.GsonBuilder
import com.hzrelaper.hospitalmachine.application.App
import com.hzrelaper.hospitalmachine.nettools.int.QuestionService
import com.hzrelaper.hospitalmachine.nettools.int.SuggestionService
import com.hzrelaper.hospitalmachine.nettools.int.UserService
import com.hzrelaper.hospitalmachine.url.StaticUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {
    private fun getClient(): Retrofit {
        return  App.getInstance().netEngine
    }

    fun getUserService(): UserService {
        val service = getClient().create(UserService::class.java)
        return service
    }

    fun getQuestionService(): QuestionService {
        val service = getClient().create(QuestionService::class.java)
        return service
    }

    fun getSuggestinoService(): SuggestionService {
        val service = getClient().create(SuggestionService::class.java)
        return service
    }

}