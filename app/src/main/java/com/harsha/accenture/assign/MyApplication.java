package com.harsha.accenture.assign;

import android.app.Application;

import com.harsha.accenture.assign.utils.DevUtils;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DevUtils.initFlipper(this);
    }

}
