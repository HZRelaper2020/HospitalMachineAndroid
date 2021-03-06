package com.hzrelaper.hospitalmachine.ui.main

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hzrelaper.hospitalmachine.R
import com.hzrelaper.hospitalmachine.application.App
import com.hzrelaper.hospitalmachine.data.pref.SharePref
import com.hzrelaper.hospitalmachine.ui.login.LoginActivity
import com.hzrelaper.hospitalmachine.ui.main.fragment.TopicsFragment
import com.hzrelaper.hospitalmachine.ui.main.fragment.otherinfo.OtherInfoFragment
import com.loveplusplus.update.HttpUtils
import com.loveplusplus.update.UpdateChecker
import com.roughike.bottombar.BottomBar


class HomeActivity : AppCompatActivity() {
    private lateinit var sharepref: SharePref

    private  var currentTabId= 0

    companion object{
        var updateChecked:Boolean?=null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharepref = SharePref(this)
        setContentView(R.layout.activity_home)


        getLatestServerIp()

        if (checkIsNeedLogin())
            return

        checkApkUpdate()

        setBottomBar()
    }

    fun getLatestServerIp(){
        Thread{
            val serveraddress = HttpUtils.get("http://149.129.58.74/config/yi_bao_machine_server_ip.php")
            if (!TextUtils.isEmpty(serveraddress)) {
                App.getInstance().serverAddress = serveraddress
            }
        }.start()
    }

    fun checkApkUpdate(){
        if (updateChecked == null || updateChecked == false) {
            updateChecked = true
            var url = App.getInstance().serverAddress + "/api/update?device=android"
            UpdateChecker.setUpdateUrl(url);
            UpdateChecker.checkForDialog(this);
        }
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

    fun onBottomClick(tabId: Int){
//            if (currentTabId != tabId){
        currentTabId = tabId

        var obj: Any? = null
        if (tabId == R.id.tab_topics) {
            obj = TopicsFragment()
        }else if (tabId == R.id.tab_my){
            val bundle = Bundle()
            bundle.putString("userId", sharepref.getUserId())
            obj = TopicsFragment()
            (obj as Fragment).arguments = bundle
        }else if (tabId == R.id.tab_others){
            obj = OtherInfoFragment()
        }

        if (obj != null)
        {
            supportFragmentManager.beginTransaction().replace(R.id.framelyout, obj as Fragment).commit()
        }
//            }
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