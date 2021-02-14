package com.test.agilemusic.application;

import android.app.Application;
import android.content.Context;

public class AgileMusicApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
//        AndroidNetworking.initialize(getApplicationContext());
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
