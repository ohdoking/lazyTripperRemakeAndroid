package com.yapp.lazitripper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yapp.lazitripper.R;

import static com.yapp.lazitripper.R.id.mypageBtn;

public class MainActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();

    }
    
    public void test(){
        findViewById(R.id.chooseBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ChoosePlaceActivity.class);
                startActivity(i);
            }
        });

        findViewById(mypageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MyProfileActivity.class);
                startActivity(i);
            }
        });
    }
}
