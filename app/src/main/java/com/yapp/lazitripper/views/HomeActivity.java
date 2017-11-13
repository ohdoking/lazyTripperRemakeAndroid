package com.yapp.lazitripper.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yapp.lazitripper.R;

import com.yapp.lazitripper.dto.PickDate;
import com.yapp.lazitripper.dto.RemainingDay;
import com.yapp.lazitripper.store.ConstantStore;
import com.yapp.lazitripper.store.SharedPreferenceStore;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


// 메인 화면

public class HomeActivity extends BaseAppCompatActivity {

    LinearLayout linearLayout;
    private String email;
    private final String TAG = "HomeActivity";
    private String key;
    private String date;
    private Date tempDate;
    private PickDate pickDate;
    private DatabaseReference myRef;
    private SharedPreferenceStore sharedPreferenceStore;

    String uuid;
    boolean isdata = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setHeader();
        pickDate = new PickDate();

        sharedPreferenceStore = new SharedPreferenceStore(getApplicationContext(), ConstantStore.STORE);
        myRef = FirebaseDatabase.getInstance().getReference("lazitripper");
        ImageView rightImage = getRightImageView();
        rightImage.setImageResource(R.drawable.ic_person_black_36dp);
        uuid = (String) sharedPreferenceStore.getPreferences(ConstantStore.UUID, String.class);

        TextView emailTv = (TextView) findViewById(R.id.textView2);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = "";

        if (user != null) {
            userName = user.getDisplayName();
            //shared에 유저명 추가
            sharedPreferenceStore.savePreferences(ConstantStore.USERNAME, userName);
        }
        //username이 길면 밑의 text가 짤림
        emailTv.setText(userName + "님,\n편하게 여행을\n만들어보세요:-)");
        //오른쪽 마이페이지 버튼
        rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
            }
        });

        //여행 만들기
        Button btnMakeRoot = (Button) findViewById(R.id.btnMakeRoot);
        btnMakeRoot.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeActivity.this, DatePickActivity.class);
                        startActivity(intent);
                    }
                }
        );

        myRef.child("user").child(uuid).child("needSelect").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                isdata = false;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    RemainingDay day = postSnapshot.getValue(RemainingDay.class);
                    Log.e(TAG, "Get key" + day.getKey());
                    key = day.getKey();
                    //현재는 무조건 리스트의 가장 첫번째 날만 작성 가능하도록 해 둠
                    date = day.getDayRemaining().get(0);
                    Log.e(TAG, "remaining day : " + date);
                    if (day.getKey() != null) isdata = true;
                }
                if (isdata) {
                    try {
                        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
                        tempDate = sdFormat.parse(date);
                        pickDate.setStartDate(tempDate);
                        pickDate.setFinishDate(tempDate);
                        pickDate.setPeriod(1L);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

//                    notification();
                } else {
                    sharedPreferenceStore.savePreferences(ConstantStore.REMAINFLAG, "false");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void notification() {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(HomeActivity.this);
        alert_confirm.setMessage("이전에 작성 중이던 루트가 있습니다. 마저 작성하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //YES
                        sharedPreferenceStore.savePreferences(ConstantStore.KEY, key);
//                        sharedPreferenceStore.savePreferences(ConstantStore.DATEKEY, pickDate);
//                        sharedPreferenceStore.savePreferences(ConstantStore.SCHEDULE_DATE, pickDate);
                        sharedPreferenceStore.savePreferences(ConstantStore.REMAINFLAG, "true");
                        //전체일정과 선택일정이 같음(하루만 가능하게)
                        Intent i = new Intent(HomeActivity.this, ChooseCityActivity.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.fade_in);

                    }
                }).setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //취소를 누른경우 needSelect를 삭제함

                        sharedPreferenceStore.savePreferences(ConstantStore.REMAINFLAG, "false");
                        DatabaseReference userRef = myRef.child("user").child(uuid);
                        DatabaseReference needSelectRef = userRef.child("needSelect");
                        userRef.child("Travel").child(key).setValue(null);
                        needSelectRef.setValue(null);

                        Intent i = new Intent(HomeActivity.this, ChooseCityActivity.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.fade_in);

                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);
        String DEBUG_TAG = "dd";
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE):
                Log.d(DEBUG_TAG, "Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP):
                Log.d(DEBUG_TAG, "Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL):
                Log.d(DEBUG_TAG, "Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.d(DEBUG_TAG, "Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
}
