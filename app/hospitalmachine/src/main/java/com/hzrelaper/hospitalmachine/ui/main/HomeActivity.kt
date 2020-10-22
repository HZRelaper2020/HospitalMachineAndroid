package com.hzrelaper.hospitalmachine.ui.main

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hzrelaper.hospitalmachine.R
import com.hzrelaper.hospitalmachine.data.pref.SharePref
import com.hzrelaper.hospitalmachine.ui.login.LoginActivity
import com.hzrelaper.hospitalmachine.ui.main.fragment.TopicsFragment
import com.roughike.bottombar.BottomBar


class HomeActivity : AppCompatActivity() {
    private lateinit var sharepref: SharePref

    private  var currentTabId= 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        getSupportActionBar()?.hide()

        sharepref = SharePref(this)
        setContentView(R.layout.activity_home)

        if (checkIsNeedLogin())
            return

        checkUpdate()

        setBottomBar()

    }

    fun setBottomBar(){
        val bottomBar = findViewById<View>(R.id.bottomBar) as BottomBar
        bottomBar.setOnTabSelectListener { tabId ->
            onBottomClick(tabId)
        }

        bottomBar.setOnTabReselectListener { tabId ->
            onBottomClick(tabId)
        }
    }

    fun onBottomClick(tabId:Int){
//            if (currentTabId != tabId){
        currentTabId = tabId

        var obj: Any? = null
        if (tabId == R.id.tab_topics) {
            obj = TopicsFragment()
        }else if (tabId == R.id.tab_my){

        }else if (tabId == R.id.tab_others){

        }

        supportFragmentManager.beginTransaction().replace(R.id.framelyout, obj as Fragment).commit()
//            }
    }
    fun checkUpdate(){

    }

    fun checkIsNeedLogin():Boolean{
        var username = sharepref.getUsername()
        if (TextUtils.isEmpty(username)) {
            var intent = Intent(this, LoginActivity::class.java);
            startActivity(intent)
            finish()
            return true
        }
        return false
    }
}