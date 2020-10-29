package com.hzrelaper.hospitalmachine.nettools.int

import com.hzrelaper.hospitalmachine.nettools.ApiClient
import retrofit2.Callback

class AnswerServiceImp {

    private lateinit var service:AnswerService

    private fun getService(): AnswerService {
        return  ApiClient().getAnswerService()
    }

    fun getAnswersByQuestionId(id:Int,callback: Callback<AnswerResult?>){
        var entity = AnswerEntityRequest()
        entity.action = "getByQuestionId"
        entity.questionId = id
        getService()?.getAnswersByQuestionId(entity).enqueue(callback)
    }

    fun addAnswer(username:String,questionId:Int,answerBody:String,callback: Callback<AnswerResult?>){
        var entity = AnswerEntity()
        entity.action = "add"
        entity.username = username
        entity.questionId = questionId
        entity.answerBody = answerBody
        getService().addAnswer(entity).enqueue(callback)
    }
}