package com.hzrelaper.hospitalmachine.nettools.int

import com.hzrelaper.hospitalmachine.nettools.ApiClient
import retrofit2.Callback

class QuestionServiceImp {
    private lateinit var service:QuestionService

    private fun getService(): QuestionService {
        return  ApiClient().getQuestionService()
    }

    fun getList(start:Int, len:Int, searchtext:String, status:Int,userId:Int, callback: Callback<QuestionResult?>){
        val req = QuestionEntityRequest()
        req.action="get"
        req.start = start
        req.length = len
        req.searchText = searchtext
        req.status = status
        req.userId = userId
        getService().getlist(req).enqueue(callback)
    }

    fun addQuestion(username:String,title:String,description:String,callback: Callback<QuestionResult?>){
        var req = QuestionAddRequest()
        req.username = username
        req.title = title
        req.description = description
        getService().addQuestion(req).enqueue(callback)
    }
}