package com.hzrelaper.hospitalmachine.data.pref

import android.content.Context
import android.content.SharedPreferences

class SharePref(private val context: Context) {
    private  val NAME = "share_pref"
    private  val MODE = Context.MODE_PRIVATE
    private  var preferences: SharedPreferences?=null

    init{
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    fun getServerAddress(): String? {
        return preferences?.getString("server_address","http://192.168.5.13:8850")
    }

    fun setServerAddress(serveraddress:String?){
        val edit = preferences?.edit()
        edit?.putString("server_address",serveraddress)
        edit?.apply()
    }

    fun getUsername(): String? {
        return preferences?.getString("username",null)
    }

    fun setUsername(username:String?){
        var edit = preferences?.edit()
        edit?.putString("username",username)
        edit?.apply()
    }

    fun getUserId(): String? {
        return preferences?.getString("userid",null)
    }

    fun setUserId(userid:String?){
        var edit = preferences?.edit()
        edit?.putString("userid",userid)
        edit?.apply()
    }
}