package com.hzrelaper.hospitalmachine.application;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hzrelaper.hospitalmachine.data.pref.SharePref;
import com.hzrelaper.hospitalmachine.url.StaticUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static App sInstance;

    private Retrofit mEngine;

    private String mServerAddress;
    private SharePref mSharePref;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mSharePref = new SharePref(getApplicationContext());

        initRetrofit();
        initXUpdate();
    }

    public void setServerAddress(String address){
        if (!TextUtils.isEmpty(address)){
            mServerAddress = address;
            mSharePref.setServerAddress(mServerAddress);
        }
    }

    public String getServerAddress(){
        if (TextUtils.isEmpty(mServerAddress))
            return  mSharePref.getServerAddress();
        return mServerAddress;
    }

    private void initRetrofit(){

//            if (mEngine == null ) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                String serverAddress = mSharePref.getServerAddress();
                mEngine = new Retrofit.Builder()
                        .baseUrl(serverAddress)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
//            }

    }
    public  void initXUpdate(){
//        XUpdate.get()
//                .debug(true)
//                .isWifiOnly(true)                                               //默认设置只在wifi下检查版本更新
//                .isGet(true)                                                    //默认设置使用get请求检查版本
//                .isAutoMode(false)                                              //默认设置非自动模式，可根据具体使用配置
//                .param("versionCode", UpdateUtils.getVersionCode(this))         //设置默认公共请求参数
//                .param("appKey", getPackageName())
//                .setOnUpdateFailureListener(new OnUpdateFailureListener() {     //设置版本更新出错的监听
//                    @Override
//                    public void onFailure(UpdateError error) {
//                        if (error.getCode() != CHECK_NO_NEW_VERSION) {          //对不同错误进行处理
////                            ToastUtils.toast(error.toString());
//                        }
//                    }
//                })
//                .supportSilentInstall(true)                                     //设置是否支持静默安装，默认是true
//                .setIUpdateHttpService(new OKHttpUpdateHttpService())           //这个必须设置！实现网络请求功能。
//                .init(this);
    }

    public static App getInstance() {
        return sInstance;
    }

    public Retrofit getNetEngine(){
        return mEngine;
    }
}