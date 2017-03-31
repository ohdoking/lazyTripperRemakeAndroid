package com.yapp.lazitripper.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.Rating;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.vision.face.Landmark;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

//상세보기 페이지
public class MyProfileActivity extends BaseAppCompatActivity {
    private String TAG = "MyProfileActivity";
    private RatingBar rating_avg;
    private float avg;
    private Review review;
    private PopupWindow pwindo;
    private float tot; //DB에서 불러들인 리뷰들의 평점의 총합
    private int review_size; //DB에서 불러들인 리뷰들의 개수

    private Button btnClosePopup;
    private Button btnOpenPopup;
    private Button btnComplete;
    private EditText comment_edt;
    private ArrayList<Review> reviewList;

    private int mWidthPixels, mHeightPixels;
    private RatingBar ratingbar;
    private View header;
    private ReviewAdapter adapter;
    private String uuid;
    private SharedPreferenceStore sharedPreferenceStore;
    private String username;
    private ListView listView;
    private ScrollView mScrollview;
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
        setContentView(R.layout.activity_my_profile2);
        setHeader();
        review = new Review();
        reviewList = new ArrayList<Review>();
        tot = 0;
        review_size = 0;
        adapter = new ReviewAdapter();
        landMark = new Travel();
        LinearLayout layout_header = (LinearLayout) findViewById(R.id.layout_header);

        sharedPreferenceStore = new SharedPreferenceStore(getApplicationContext(), ConstantStore.STORE);
        uuid = (String) sharedPreferenceStore.getPreferences(ConstantStore.UUID, String.class);
        username = (String) sharedPreferenceStore.getPreferences(ConstantStore.USERNAME, String.class);
        listView = (ListView) findViewById(R.id.listview);
        final View header = getLayoutInflater().inflate(R.layout.layout_header, listView, false);

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        ViewGroup.LayoutParams params = header.getLayoutParams();
        params.height = height-250;
        params.width = width;
        header.setLayoutParams(params);

       /* header.setLayoutParams(new
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));*/
        listView.addHeaderView(header);
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

        ImageView background = (ImageView)findViewById(R.id.backgroundImage);
        TextView title = (TextView)findViewById(R.id.name);
        ImageView country_image = (ImageView)findViewById(R.id.country_image);
        TextView _addr = (TextView) findViewById(R.id._addr);
        TextView addr = (TextView) findViewById(R.id.addr);
        TextView _tel = (TextView) findViewById(R.id._tel);
        TextView tel = (TextView) findViewById(R.id.tel);

        Glide.with(this).load(handledata.getFirstimage()).override(540, 830).centerCrop().into(background);
        title.setText(handledata.getTitle());
        country_image.setImageDrawable(getResources().getDrawable(R.drawable.korea));
        _addr.setText("ADD");
        addr.setText(handledata.getAddr1());
        _tel.setText("TEL");
        tel.setText(handledata.getTel());

        //initlayout();
        getTravel();
/*
        listView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                mScrollview.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
*/
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
                         Travel data = child.getValue(Travel.class);
                         Review review = data.getReview();
                         Log.e(TAG, "저장 데이터 : " + review.getComment());

                         reviewList.add(review);
                         review_size += 1;
                         tot += review.getRating();
                     }

                     landMark.setReviewList(reviewList);
                     adapter.addItem(landMark);

                     if (0 < review_size)
                         avg = (float) tot/ review_size;

                     rating_avg = (RatingBar) findViewById(R.id.rating_avg);
                     rating_avg.setStepSize((float) 0.5);
                     //rating_avg.setMax(5);
                     //만약 해당 랜드마크의 평균별점이 0이상이면 평균별점 초기값 세팅
                     if (0 < avg) {
                         rating_avg.setRating(avg);
                     }
                     adapter.notifyDataSetChanged();
                 }
                 @Override
                 public void onCancelled(DatabaseError databaseError) {
                 }
             });
         }
     }
}
