package com.yapp.lazitripper.views.bases;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by ohdok on 2017-02-26.
 */

public class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}
