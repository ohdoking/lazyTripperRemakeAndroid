package com.yapp.lazitripper.common;

import android.app.Application;

import com.tsengvn.typekit.Typekit;
/*
    Global Application 설정

    폰트 설정
*/
public class LazitripperApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NotoSansKR-Regular-Hestia.otf"))
                .addCustom1(Typekit.createFromAsset(this, "fonts/NotoSansKR-Thin-Hestia.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NotoSansKR-Bold-Hestia.otf"));
    }


}