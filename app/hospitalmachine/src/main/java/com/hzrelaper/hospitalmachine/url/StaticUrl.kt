package com.hzrelaper.hospitalmachine.url

class StaticUrl {
    companion object{
        val BaseUrl = "http://192.168.5.26:51520"
        class User{
            companion object{
                val LoginUrl = BaseUrl + "/User"
            }
        }
    }
}