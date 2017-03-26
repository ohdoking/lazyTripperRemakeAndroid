package com.yapp.lazitripper.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.Rating;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.PlaceInfoDto;
import com.yapp.lazitripper.dto.Review;
import com.yapp.lazitripper.dto.Travel;
import com.yapp.lazitripper.store.ConstantStore;
import com.yapp.lazitripper.store.SharedPreferenceStore;
import com.yapp.lazitripper.views.adapters.ReviewAdapter;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

//상세보기 페이지
public class MyProfileActivity extends BaseAppCompatActivity {
    private String TAG = "MyProfileActivity";
    private RatingBar rating_avg;
    private float avg;
    private Review review;
    private PopupWindow pwindo;

    private Button btnClosePopup;
    private Button btnOpenPopup;
    private Button btnComplete;
    private EditText comment_edt;

    private int mWidthPixels, mHeightPixels;
    private RatingBar ratingbar;
    private View header;
    private ReviewAdapter adapter;
    private String uuid;
    private SharedPreferenceStore sharedPreferenceStore;
    private String username;
    private ListView listView;

    private Travel landMark;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("lazitripper");

    //여행지 선택 화면과 동일하게 지명, 주소, 전화번호, 평균별점, 국기
    //해당 장소가 가지고 있는 키워드(관광 정보 서비스 분류 코드) 데이터 뿌려줌
    // 사용자가 남긴 모든 별점 및 리뷰를 뿌려줌(리뷰 남긴 순)
    // 마이페이지의 완성된 루트를 통해 상세보기에 들어왔을 때 별점 밀 리뷰 작성 기능
    // 본인이 작성한 별점 및 리뷰 수정 삭제.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        TextView title_txt = (TextView) findViewById(R.id.title_txt);
        TextView address_txt = (TextView) findViewById(R.id.address_txt);
        setHeader();
        review = new Review();

        adapter = new ReviewAdapter();
        landMark = new Travel();
        sharedPreferenceStore = new SharedPreferenceStore(getApplicationContext(), ConstantStore.STORE);
        uuid = (String) sharedPreferenceStore.getPreferences(ConstantStore.UUID, String.class);
        username = (String) sharedPreferenceStore.getPreferences(ConstantStore.USERNAME, String.class);
        listView = (ListView) findViewById(R.id.review_listview);

        listView.setAdapter(adapter);

        //NXE 예방
        if (uuid == null) uuid = "null";
        if (username == null) username = "null";

        review.setUserkey(uuid);
        review.setUsername(username);

        Intent intent = getIntent();
        PlaceInfoDto handledata = (PlaceInfoDto) intent.getSerializableExtra("placeInfo");

        landMark.setTel(handledata.getTel());
        landMark.setAddress(handledata.getAddr1());
        landMark.setTel(handledata.getTel());
        landMark.setTitle(handledata.getTitle());

        initlayout();

        //PlaceInfoDto landmark = new PlaceInfoDto(handledata.getAddr1(),handledata.getTel(),handledata);

        LinearLayout btn_layout = (LinearLayout) findViewById(R.id.layout_btn);

        btn_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_rating:
                        initiatePopupWindow();
                        break;
                }
            }
        };

        btnOpenPopup = (Button) findViewById(R.id.btn_rating);
        btnOpenPopup.setOnClickListener(onClickListener);

        title_txt.setText(handledata.getTitle());
        address_txt.setText("ADD :" + handledata.getAddr1());

        if (0 < handledata.getRating_tot())
            avg = (float) (handledata.getRating_tot() / handledata.getReview().size());

        rating_avg = (RatingBar) findViewById(R.id.rating_avg);
        rating_avg.setStepSize((float) 0.5);
        rating_avg.setMax(5);

        //만약 해당 랜드마크의 평균별점이 0이상이면 평균별점 초기값 세팅
        if (0 < avg) {
            rating_avg.setRating(avg);
        }
        //DatabaseException 발생
        getTravel();

    }
    private void initlayout(){
        WindowManager w = getWindowManager();
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        // since SDK_INT = 1;
        mWidthPixels = metrics.widthPixels;
        mHeightPixels = metrics.heightPixels;

        // 상태바와 메뉴바의 크기를 포함해서 재계산
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
            try {
                mWidthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
                mHeightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
            } catch (Exception ignored) {
            }
        // 상태바와 메뉴바의 크기를 포함
        if (Build.VERSION.SDK_INT >= 17)
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                mWidthPixels = realSize.x;
                mHeightPixels = realSize.y;
            } catch (Exception ignored) {
            }
    }

    private void initiatePopupWindow() {
        try {
            //  LayoutInflater 객체와 시킴
            LayoutInflater inflater = (LayoutInflater) MyProfileActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.layout_popup,
                    (ViewGroup) findViewById(R.id.popup_element));

            pwindo = new PopupWindow(layout, mWidthPixels-100, mHeightPixels-1400, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
            btnClosePopup.setOnClickListener(listener_in_popup);
            comment_edt = (EditText)layout.findViewById(R.id.edit_comment);

            btnComplete = (Button)layout.findViewById(R.id.btn_compelete);
            btnComplete.setOnClickListener(listener_in_popup);

            ratingbar =(RatingBar)layout.findViewById(R.id.ratingBar);
            ratingbar.setStepSize((float)0.5);
            ratingbar.setMax(5);
            ratingbar.setOnRatingBarChangeListener(rating_listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener listener_in_popup =
            new View.OnClickListener() {

                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.btn_compelete:
                            if(comment_edt.getText().toString() != null){
                                review.setComment(comment_edt.getText().toString());
                                review.setRating(ratingbar.getRating());
                                landMark.setReview(review);
                                adapter.addItem(landMark);
                                saveData(landMark);
                                getTravel();
                                pwindo.dismiss();
                                //// TODO: 2017-03-25 DB작업 해야하고, loginActivty를 거치지 않으면 username을 못받아옴.
                            }
                            break;
                        case R.id.btn_close_popup:
                            pwindo.dismiss();
                            break;
                    }
                }
            };

    private RatingBar.OnRatingBarChangeListener rating_listener =
            new RatingBar.OnRatingBarChangeListener(){
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating,
                                            boolean fromUser) {
                    Log.d(TAG,"RATING : " + rating);
                }
    };

    private void saveData(Travel landMark){
        myRef.child("landmark").child(landMark.getTitle()).child(uuid).setValue(landMark);
    }

     /*
        * childadded 이벤트 리스너에서 Travel 데이터를 받아서 해당 Travel의 review의 rating과 review의 수를 받아서
        * rating_tot를 통해 rating_avg 를 뿌려준다.
        *
        * */
     private void getTravel() {
         if (landMark.getTitle() != null) {
             myRef.child("landmark").child(landMark.getTitle()).addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot dataSnapshot) {
                     for (DataSnapshot child : dataSnapshot.getChildren()) {
                         //saveData(child.getValue(Travel.class));
                         Review review = child.getValue(Review.class);
                         if (review.getComment() != null)
                             Log.e(TAG, "저장 데이터 : " + review.getComment());
                     }
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });
         }
     }
}
