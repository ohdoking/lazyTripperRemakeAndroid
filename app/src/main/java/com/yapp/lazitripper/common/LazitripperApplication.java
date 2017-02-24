package com.yapp.lazitripper.common;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

public class LazitripperApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NotoSansCJKkr-Thin.otf"));
    }


}