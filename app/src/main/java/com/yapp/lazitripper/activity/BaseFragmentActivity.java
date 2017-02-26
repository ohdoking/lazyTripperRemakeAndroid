package com.yapp.lazitripper.activity;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.tsengvn.typekit.TypekitContextWrapper;
import com.yapp.lazitripper.R;

/**
 * Created by ohdok on 2017-02-26.
 */

public class BaseFragmentActivity extends FragmentActivity {

    View mCustomView;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

//    public void setHeader(){
//        ActionBar actionBar = ge();
////        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
////        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(false);
//        actionBar.setDisplayShowTitleEnabled(false);
//
//        mCustomView = LayoutInflater.from(this).inflate(R.layout.header,null);
//        actionBar.setCustomView(mCustomView);
//
//        Toolbar parent = (Toolbar) mCustomView.getParent();
//        parent.setContentInsetsAbsolute(0,0);
//
//        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.facebook_btn));
//
//        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
//                ActionBar.LayoutParams.WRAP_CONTENT);
//        actionBar.setCustomView(mCustomView, params);
//    }
//
//    public ImageView getLeftImageView(){
//        return (ImageView) mCustomView.findViewById(R.id.drawer_imageview);
//    }
//    public ImageView getRightImageView(){
//        return (ImageView) mCustomView.findViewById(R.id.drawer_imageview_done);
//    }

}
