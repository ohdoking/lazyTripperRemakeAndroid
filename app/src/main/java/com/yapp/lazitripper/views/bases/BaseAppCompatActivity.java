package com.yapp.lazitripper.views.bases;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.tsengvn.typekit.TypekitContextWrapper;
import com.yapp.lazitripper.R;

public class BaseAppCompatActivity extends AppCompatActivity {

    public View mCustomView;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    /*
    *
    * ActionBar 뷰를 만든다.
    * 참고 해보기 : http://stackoverflow.com/questions/16079028/how-to-get-onclicklistener-event-on-custom-actionbar
    *
    *
    * */
    public void setHeader(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        mCustomView = LayoutInflater.from(this).inflate(R.layout.header,null);
        actionBar.setCustomView(mCustomView);

        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0,0);

//        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.facebook_btn));

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        actionBar.setCustomView(mCustomView, params);
    }




    public void setMyHeader(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setElevation(0); // 그림자 없애기

        mCustomView = LayoutInflater.from(this).inflate(R.layout.myheader,null);
        actionBar.setCustomView(mCustomView);
        mCustomView.setBackgroundColor(Color.rgb(230,230,230));


        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0,0);

//        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.facebook_btn));

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        actionBar.setCustomView(mCustomView, params);
    }
    /*
    *
    * actionbar 의 왼쪽 버튼의 인스턴스를 가져온다
    *
    * */
    public ImageView getLeftImageView(){
        return (ImageView) mCustomView.findViewById(R.id.drawer_imageview);
    }

    /*
    *
    * actionbar 의 오른쪽 버튼의 인스턴스를 가져온다
    *
    * */
    public ImageView getRightImageView(){
        return (ImageView) mCustomView.findViewById(R.id.drawer_imageview_done);
    }

}