package com.yapp.lazitripper.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.AllTravelInfo;
import com.yapp.lazitripper.store.ConstantStore;
import com.yapp.lazitripper.store.SharedPreferenceStore;
import com.yapp.lazitripper.util.FirebaseService;
import com.yapp.lazitripper.views.adapters.RecentTravelAdapter;
import com.yapp.lazitripper.views.adapters.RecyclerItemClickListener;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

public class ProfileActivity extends BaseAppCompatActivity {
    private static String TAG = "dongs";
    private List<AllTravelInfo> travelList = new ArrayList<>();
    private RecentTravelAdapter adapter;
    private RecyclerView recyclerTavel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ( (ImageView) findViewById(R.id.iv_profile_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        ( (ImageView) findViewById(R.id.iv_profile_logout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });
        ((TextView)findViewById(R.id.my_travel_key_update)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,KeywordActivity.class);
                i.putExtra("init",false);//KeywordActivity Flag 추가
                startActivity(i);
            }
        });
        recyclerTavel = (RecyclerView) findViewById(R.id.recycler_profile_travel_list);
        String userId = Profile.getCurrentProfile().getId();
        String profileURL = "http://graph.facebook.com/" + userId + "/picture?type=large";
        ImageView profileImageView = (ImageView) findViewById(R.id.user_profile_photo);
        Glide.with(this).load(profileURL).into(profileImageView);
        TextView nameText = (TextView) findViewById(R.id.user_profile_name);
        nameText.setText(Profile.getCurrentProfile().getName());
        recyclerTavel.addOnItemTouchListener(new RecyclerItemClickListener(ProfileActivity.this, recyclerTavel, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(),position+"번째",Toast.LENGTH_LONG);
            }
        }));

        SharedPreferenceStore<String[]> sharedPreferenceStore = new SharedPreferenceStore<String[]>(getApplicationContext(), ConstantStore.STORE);
        String[] tagList = sharedPreferenceStore.getPreferences(ConstantStore.TAGS, String[].class);

        //태그 그룹에 # 추가
        int index =0;
        if(tagList.length!=0)
        for(String tag : tagList){
            tagList[index] =  "#" + tag;
            index++;
        }
        else {
            tagList = new String[1];
            tagList[0] = "#태그를 선택해주세요";
        }
        TagGroup mTagGroup = (TagGroup) findViewById(R.id.tag_group);
        mTagGroup.setTags(tagList);

    }


    @Override
    public void onResume() {
        super.onResume();

        travelList = FirebaseService.getInstance().getTravelList();
        recentTravelListSetting();
    }

    private void recentTravelListSetting() {

        adapter = new RecentTravelAdapter(getApplicationContext(), travelList, R.layout.item_route);
        recyclerTavel.setAdapter(adapter);
        recyclerTavel.setHasFixedSize(true);
        recyclerTavel.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter.notifyDataSetChanged();
    }


     /*   final Activity ctx = context;
        rDatabase.child("user").child("mDH4gS4mJDaKLqmfbosrvHVBkHE3").child("Travel").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot route : dataSnapshot.getChildren()) {
                    travelRouteMap = new HashMap<>();
                    Iterator<DataSnapshot> iterator = route.getChildren().iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot dataSnap = iterator.next();
                        travelRouteList = new ArrayList<>();
                        //Log.e(TAG,dataSnap.toString());
                        Iterator<DataSnapshot> routeIterator = dataSnap.getChildren().iterator();
                        while (routeIterator.hasNext()) {
                            DataSnapshot routeDataSnap = routeIterator.next();
                            travelRouteList.add(routeDataSnap.getValue(TravelRouteDto.class));
                        }
                        travelRouteMap.put(dataSnap.getKey(), travelRouteList);
                    }
                    travelTotalList.add(travelRouteMap);
                    travelTotalList.add(travelRouteMap);
                    travelTotalList.add(travelRouteMap);
                    travelTotalList.add(travelRouteMap);
                }
//                .setLayoutParams(new RelativeLayout.LayoutParams(Layouarams.FILL_PARENT, theSizeIWant));
                listView.setMinimumHeight(130*travelTotalList.size());
                ScrollInfiniteAdapter adapter = new ScrollInfiniteAdapter(ctx, travelTotalList, R.layout.item_route, 5, 5) {
                    @Override
                    public View getView(final int position, View convertView, ViewGroup parent) {

                        View view = null;

                        HashMap<String, ArrayList<TravelRouteDto>> routeMap = (HashMap<String, ArrayList<TravelRouteDto>>) getItem(position);
                        Object[] keyList = routeMap.keySet().toArray();
                        String[] stringKeyList = Arrays.copyOf(keyList, keyList.length, String[].class);

                        Arrays.sort(stringKeyList);

                        if (convertView == null) {
                            view = LayoutInflater.from(getContext()).inflate(R.layout.item_route, null);

                        } else {
                            view = convertView;
                        }
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(ProfileActivity.this,"Position : "+position+"여기 연결해야되는 액티비티 머야",Toast.LENGTH_SHORT).show();
                            }
                        });

                        ivProfileItemBg = (ImageView) view.findViewById(R.id.iv_item_route_backgound);
                        Glide.with(ctx)
                                .load(routeMap.get(stringKeyList[0]).get(0).getImageURL())
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .bitmapTransform(new CenterCrop(ProfileActivity.this),new RoundedCornersTransformation( ProfileActivity.this,60, 2))
                                .into(ivProfileItemBg);

                        String[] firstList = stringKeyList[0].toString().split("@");

                        String firstDate = firstList[0];

                        TextView travelDatatv = (TextView) view.findViewById(R.id.travel_date);
                        travelDatatv.setText(firstDate + "작성");

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
        });*/

    //지역 코드를 지역 명으로 변환해준다
    private String makeTitleName(Integer cityCode) {

        String name = null;

        switch (cityCode) {
            case 1:
                name = "서울";
                break;
            case 2:
                name = "인천";
                break;
            case 3:
                name = "대전";
                break;
            case 4:
                name = "대구";
                break;
            case 5:
                name = "광주";
                break;
            case 6:
                name = "부산";
                break;
            case 7:
                name = "울산";
                break;
            case 8:
                name = "세종특별자치시";
                break;
            case 31:
                name = "경기도";
                break;
            case 32:
                name = "강원도";
                break;
            case 33:
                name = "충청북도";
                break;
            case 34:
                name = "충청남도";
                break;
            case 35:
                name = "경상북도";
                break;
            case 36:
                name = "경상남도";
                break;
            case 37:
                name = "전라북도";
                break;
            case 38:
                name = "전라남도";
                break;
            case 39:
                name = "제주도";
                break;
            default:
                new Exception("no place code");
                break;
        }
        return name;
    }

}
