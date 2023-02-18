package com.example.api.common.app;

import android.app.Application;
import android.content.res.Configuration;

import com.example.api.common.helper.LangUtils;
import com.example.api.data.source.local.MyRoomDB;

import java.util.Locale;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MyRoomDB.getDatabaseInstance(this);

    }

}
