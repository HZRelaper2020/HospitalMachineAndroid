package com.hzrelaper.hospitalmachine.nettools.int

import java.text.SimpleDateFormat
import java.util.*

class QuestionEntity{
    var id :Int?= null
    var name:String?=null
    var authorId:String?=null
    var status:Int?=null
    var replys:Int?=null
    var questionTime:String?=null
    fun renderTime():String?{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var pubtime = dateFormat.parse(questionTime)
        return getTimeStr(pubtime,null)
    }

    fun getTimeStr(ctime: Date?, format: String?): String? {
        var format = format
        var r = ""
        if (ctime == null) return r
        if (format == null) format = "MM-dd HH:mm"
        val nowtimelong = System.currentTimeMillis()
        val ctimelong = ctime.time
        val result = Math.abs(nowtimelong - ctimelong)
        if (result < 60000) { // 一分钟内
            val seconds = result / 1000
            r = if (seconds == 0L) {
                "刚刚"
            } else {
                seconds.toString() + "秒前"
            }
        } else if (result >= 60000 && result < 3600000) { // 一小时内
            val seconds = result / 60000
            r = seconds.toString() + "分钟前"
        } else if (result >= 3600000 && result < 86400000) { // 一天内
            val seconds = result / 3600000
            r = seconds.toString() + "小时前"
        } else if (result >= 86400000 && result < 1702967296) { // 三十天内
            val seconds = result / 86400000
            r = seconds.toString() + "天前"
        } else { // 日期格式
            format = "MM-dd HH:mm"
            val df = SimpleDateFormat(format)
            r = df.format(ctime).toString()
        }
        return r
    }
}

class QuestionResult {
    var result: Int? = null
    var message: String? = null
    var data: List<QuestionEntity?>? = null
}

class  QuestionEntityRequest{
    var action :String?=null
    var start = 0
    var length = 0
    var searchText:String?=null
    var status =0
    var userId = 0
    var requestUserId= 0
}

class QuestionAddRequest{
    var action="add"
    var username:String?=null
    var title:String?=null
    var description:String?=null
}