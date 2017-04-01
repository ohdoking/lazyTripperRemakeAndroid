package com.yapp.lazitripper.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.frosquivel.scrollinfinite.ScrollInfiniteAdapter;
import com.frosquivel.scrollinfinite.ScrollInfiniteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yapp.lazitripper.R;

import com.yapp.lazitripper.dto.TravelRouteDto;
import com.yapp.lazitripper.store.ConstantStore;
import com.yapp.lazitripper.store.SharedPreferenceStore;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

public class ProfileActivity extends BaseAppCompatActivity {
    private ArrayList<String> tagList;
    private String uuid;
    private DatabaseReference rDatabase;
    private List<Object> travelTotalList;
    private static String TAG = "dongs";
    private ListView listView;
    private ProgressBar progressBar;
    private ArrayList<TravelRouteDto> travelRouteList;
    private HashMap<String,ArrayList<TravelRouteDto>> travelRouteMap;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setMyHeader();

        ImageView leftImage = getLeftImageView();
        leftImage.setImageResource(R.drawable.arrow);
        leftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView rightImage = getRightImageView();
        rightImage.setImageResource(R.drawable.logout_icon);
        rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });




        travelTotalList = new ArrayList<>();
        travelRouteList = new ArrayList<>();
        travelRouteMap = new HashMap<>();
        rDatabase =  FirebaseDatabase.getInstance().getReference("lazitripper");



        String userId = Profile.getCurrentProfile().getId();
        String profileURL = "http://graph.facebook.com/" + userId + "/picture?type=large";
        ImageView profileImageView = (ImageView) findViewById(R.id.user_profile_photo);
        Glide.with(this).load(profileURL).into(profileImageView);
        TextView nameText = (TextView) findViewById(R.id.user_profile_name);
        nameText.setText(Profile.getCurrentProfile().getName());


        listView = (ListView) findViewById(R.id.my_route_lv);

        getTravelList(ProfileActivity.this);
        //// TODO: 2017-03-26 My Travel Route와 DB 동기화, 터치이벤트 넣고 해당 item 클릭했을 때 상세보기(TravelSummary로?)
        // SP 에서 저장된 테그들의 정보를 가져옴.

        SharedPreferenceStore<String[]> sharedPreferenceStore = new SharedPreferenceStore<String[]>(getApplicationContext(), ConstantStore.STORE);
        String[] tagList = sharedPreferenceStore.getPreferences(ConstantStore.TAGS, String[].class);
        TagGroup mTagGroup = (TagGroup) findViewById(R.id.tag_group);
        mTagGroup.setTags(tagList);

    }

    private void getTravelList(Activity context){
        //EdQjmUKrGEWqX355Lct5EFiccR23
        //mDH4gS4mJDaKLqmfbosrvHVBkHE3
        //TP2TT0sMJgVv36vakWyW72Saz4r2


        final Activity ctx = context;
        rDatabase.child("user").child("mDH4gS4mJDaKLqmfbosrvHVBkHE3").child("Travel").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                TextView travelDay = (TextView)findViewById(R.id.travel_day);
                travelDay.setText(dataSnapshot.getChildrenCount() + "");


                for(DataSnapshot route : dataSnapshot.getChildren()){
                    travelRouteMap = new HashMap<>();
                    Iterator<DataSnapshot> iterator = route.getChildren().iterator();
                    while(iterator.hasNext()){
                        DataSnapshot dataSnap = iterator.next();
                        travelRouteList = new ArrayList<>();
                        //Log.e(TAG,dataSnap.toString());
                        Iterator<DataSnapshot> routeIterator = dataSnap.getChildren().iterator();
                        while(routeIterator.hasNext()) {
                            DataSnapshot routeDataSnap = routeIterator.next();
                            travelRouteList.add(routeDataSnap.getValue(TravelRouteDto.class));
                        }
                        travelRouteMap.put(dataSnap.getKey(),travelRouteList);
                    }
                    travelTotalList.add(travelRouteMap);
                }







                ScrollInfiniteAdapter adapter = new ScrollInfiniteAdapter(ctx, travelTotalList, R.layout.activity_route, 5, 5){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        View view=null;


                        HashMap<String,ArrayList<TravelRouteDto>> routeMap = (HashMap<String,ArrayList<TravelRouteDto>>)getItem(position);

                        Object[] keyList = routeMap.keySet().toArray();

                        String[] stringKeyList = Arrays.copyOf(keyList, keyList.length, String[].class);


                        Arrays.sort(stringKeyList);

                        if(convertView == null){
                            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_route, null);


                        }else{
                            view = convertView;
                        }

                        RelativeLayout rootview = (RelativeLayout)view.findViewById(R.id.my_route_rl);
                        Glide.with(ctx)
                                .load(routeMap.get(stringKeyList[0]).get(0).getImageURL())
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.default_thumbnail)
                                .into(new ViewTarget<RelativeLayout, GlideDrawable>(rootview) {
                                    @Override
                                    public void onResourceReady(GlideDrawable resource, GlideAnimation anim) {
                                        RelativeLayout myView = this.view;
                                        // Set your resource on myView and/or start your animation here.
                                        myView.setBackground(resource);
                                    }
                                });

                        String[] firstList = stringKeyList[0].toString().split("@");

                        String firstDate = firstList[0];
                        String firstCity = makeTitleName(Integer.parseInt(firstList[1]));

                        String[] lastList = stringKeyList[stringKeyList.length-1].toString().split("@");

                        String lastDate = lastList[0];
                        String lastCity = makeTitleName(Integer.parseInt(lastList[1]));


                        TextView travelDatatv = (TextView) view.findViewById(R.id.travel_date);
                        travelDatatv.setText(firstDate + " - " + lastDate);

                        TextView travel_space1 = (TextView) view.findViewById(R.id.travel_space1);
                        travel_space1.setText(firstCity);

                        TextView travel_space2 = (TextView) view.findViewById(R.id.travel_space2);
                        travel_space2.setText(lastCity);
                        return view;
                    }
                };




                //View footer = LayoutInflater.from(ctx).inflate(R.layout.progress_bar, null);
                //progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);
                //listView.addFooterView(footer);
                listView.setAdapter(adapter);
                //listView.setOnScrollListener(new ScrollInfiniteListener(adapter,progressBar));

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("fail", "Failed to read value.", error.toException());
            }
        });

    }

    //지역 코드를 지역 명으로 변환해준다
    private String makeTitleName(Integer cityCode) {

        String name = null;

        switch (cityCode) {
            case 1    : name = "서울";
                break;
            case 2   : name = "인천";
                break;
            case 3  : name = "대전";
                break;
            case 4  : name = "대구";
                break;
            case 5  : name = "광주";
                break;
            case 6  : name = "부산";
                break;
            case 7  : name = "울산";
                break;
            case 8  : name = "세종특별자치시";
                break;
            case 31  : name = "경기도";
                break;
            case 32  : name = "강원도";
                break;
            case 33  : name = "충청북도";
                break;
            case 34  : name = "충청남도";
                break;
            case 35  : name = "경상북도";
                break;
            case 36  : name = "경상남도";
                break;
            case 37  : name = "전라북도";
                break;
            case 38  : name = "전라남도";
                break;
            case 39  : name = "제주도";
                break;
            default    : new Exception("no place code");
                break;
        }
        return name;
    }

}
