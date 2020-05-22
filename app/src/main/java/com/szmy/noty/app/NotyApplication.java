package com.szmy.noty.app;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

public class NotyApplication extends Application {

    private Context mContext;
    private static NotyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        instance = this;
    }

    public static NotyApplication instance(){
        return instance;
    }

    public Context context() {
        if (mContext == null) mContext = getApplicationContext();
        return mContext;
    }
}
