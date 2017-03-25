package com.yapp.lazitripper.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

// 메인 화면

public class HomeActivity extends BaseAppCompatActivity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setHeader();


        /* setHeaer */
        ImageView rightImage = getRightImageView();

        //TODO 이미지 변경 혹은 아이콘 삭제
        rightImage.setImageResource(R.drawable.more);
        rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        TextView emailTv = (TextView) findViewById(R.id.textView2);
        String uuid = getIntent().getStringExtra(ConstantIntent.UUID);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = "";

        //username으로 변경
        //만약 firebase email login이나 , google login등의 방식을 사용한다면 userName이 아닌 getUserEmail을 사용해야한다.
        //공백값에 대한 처리여부

        if (user != null) {
            userName = user.getDisplayName();
        }
        //TODO 떠나고 싶은 여행을 까지만 노출됨, 전체적인 view 개선
        emailTv.setText(userName + "님,\n떠나고 싶은\n여행을 만나보세요");

        //여행 시작
        linearLayout = (LinearLayout) findViewById(R.id.nextPageBtn);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, DatePickActivity.class);
                startActivity(i);
            }
        });
    }
}
