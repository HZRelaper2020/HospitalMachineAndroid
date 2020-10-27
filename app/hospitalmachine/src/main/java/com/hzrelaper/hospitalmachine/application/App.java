package com.hzrelaper.hospitalmachine.application;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hzrelaper.hospitalmachine.url.StaticUrl;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static App sInstance;

    private Retrofit mEngine;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        if (mEngine == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            mEngine = new Retrofit.Builder()
                    .baseUrl(StaticUrl.Companion.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
    }

    public static App getInstance() {
        return sInstance;
    }

    public Retrofit getNetEngine(){
        return mEngine;
    }
}