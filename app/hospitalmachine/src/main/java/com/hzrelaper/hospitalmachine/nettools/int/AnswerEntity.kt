package com.hzrelaper.hospitalmachine.nettools.int

import java.text.SimpleDateFormat
import java.util.*

class AnswerEntity{
    var action="add"
    var id = 0
    var questionId= 0
    var answerBody=""
    var answerTime=""
    var username = ""
    var answerStatus = 0

    fun renderAnswerTime():String?{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var pubtime = dateFormat.parse(answerTime)
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

class AnswerEntityRequest{
    var action="get"
    var questionId = 0
}

class AnswerResult{
    var result = 0
    var message = ""
    var data:List<AnswerEntity>?=null
}