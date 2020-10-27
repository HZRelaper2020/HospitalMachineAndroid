package com.hzrelaper.hospitalmachine.nettools.int

import com.hzrelaper.hospitalmachine.nettools.ApiClient
import retrofit2.Callback

class SuggestinoServiceImp {

    private fun getService(): SuggestionService {
        return  ApiClient().getSuggestinoService()
    }

    fun  add(title:String,callback: Callback<SuggestionResult?>){
        var entity =SuggestionRequst()
        entity.name = title
        getService().add(entity).enqueue(callback)
    }
}