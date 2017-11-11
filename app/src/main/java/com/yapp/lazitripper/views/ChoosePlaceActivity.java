package com.yapp.lazitripper.views;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.dto.AllTravelInfo;
import com.yapp.lazitripper.dto.PlaceInfoDto;
import com.yapp.lazitripper.dto.common.CommonResponse;
import com.yapp.lazitripper.flinglibrary.SwipeFlingAdapterView;
import com.yapp.lazitripper.network.LaziTripperKoreanTourClient;
import com.yapp.lazitripper.service.LaziTripperKoreanTourService;
import com.yapp.lazitripper.store.ConstantStore;
import com.yapp.lazitripper.store.SharedPreferenceStore;
import com.yapp.lazitripper.util.TravelRoute;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;
import com.yapp.lazitripper.views.dialog.LoadingDialog;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChoosePlaceActivity extends BaseAppCompatActivity {

    //private ArrayList<ChosenPlaceItem> a;
    private ArrayList<String> b;
    private ArrayAdapter<String> arrayAdapter;
    //private ChosenPlaceAdapter adapter;
    int i = 0;

    public MyAdapter myAdapter;
    public ViewHolder viewHolder;
    private List<PlaceInfoDto> array;
    private List<PlaceInfoDto> hateList;
    SwipeFlingAdapterView flingContainer;

    public PlaceInfoDto placeInfoDto;
    public LaziTripperKoreanTourClient laziTripperKoreanTourClient;
    public LaziTripperKoreanTourService laziTripperKoreanTourService;
    private String TAG = "ChoosePlaceActivity";

    AllTravelInfo allTravelInfo;

    Integer cityCode;
    Integer day;
    Integer totalDay;
    ImageView kindTextVeiw;
    TextView discriptionCountTextView;

    TextView titlePlaceNameTextView;
    ImageView nextDayImageView;
    ImageView previewTextView;

    LoadingDialog loadingDialog;

    Integer locationCount = 0;
    //0. 랜드마크, 1. 레스토랑 2. 호텔
    Integer locationFlag = 0;
    Integer count = 0;
    Integer thisCount = 0;

    //이미 선택된 랜드마크 카운트?
    Integer landMarkUseCount = 0;

    TravelRoute travelRoute;
    // paging 처리를 위한
    Integer page = 1;
    Integer pageNum = 20;

    private Boolean exit = false;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference firebaseRef = database.getReference("lazitripper");
    DatabaseReference geoFireRef = firebaseRef.child("geofire");
    DatabaseReference userInfoRef = firebaseRef.child("user");

    DatabaseReference placeInfoRef;

    GeoQuery geoQuery;
    GeoFire geoFire;

    //km
    int radius = 1;
    Float currentLat;
    Float currentLon;

    View overlayView;

    private String uuid;
    private SharedPreferenceStore sharedPreferenceStore1;

    ArrayList<PlaceInfoDto> placeInfoDtoList = new ArrayList<PlaceInfoDto>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHeader();
        setContentView(R.layout.activity_choose_place);

        //TODO day를 받아와서 넣어줘야함
        Intent beforeIntent = getIntent();
        day = beforeIntent.getIntExtra(ConstantIntent.CURRENTDAY, 0);
        allTravelInfo = (AllTravelInfo) beforeIntent.getSerializableExtra(ConstantIntent.AllTRAVELINFO);
        totalDay = allTravelInfo.getTotalDay();
        //이전 엑티비티에서 city code를 가져옴
        cityCode = allTravelInfo.getAllTraveInfo().get(day - 1).getCityCode();

//        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
//        setProgressBarIndeterminateVisibility(true);
        array = new ArrayList<>();
        hateList = new ArrayList<>();

        initView();

        titlePlaceNameTextView.setText(makeTitleName(day, cityCode));

        //nextDayTextView.setText(makeNextDayName(day, totalDay));
        //firebase settting
        geoFire = new GeoFire(geoFireRef);
        placeInfoRef = firebaseRef.child("placeInfo").child(String.valueOf(cityCode));


        //12 관광지 데이터를 가져옴
        getPlaceData(12);
        discriptionCountTextView.setText(makeSentence(thisCount));
        renderItem();

        nextDayImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!locationCount.equals(0)) {
                    goNextActivity();
                } else {
                    Toast.makeText(ChoosePlaceActivity.this, R.string.more1, Toast.LENGTH_SHORT).show();
                }
            }
        });

        previewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allTravelInfo.getAllTraveInfo().get(day - 1).setPlaceInfoDtoList(placeInfoDtoList);
                Intent i = new Intent(ChoosePlaceActivity.this, TempScheduleActivity.class);
                i.putExtra(ConstantIntent.TEMPSCHEDULELIST, allTravelInfo);
                i.putExtra(ConstantIntent.CURRENTDAY, day);
                startActivity(i);
            }
        });

    }


    /**
     * view 를 초기화화
     */
    void initView() {
        loadingDialog = new LoadingDialog(ChoosePlaceActivity.this);
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        discriptionCountTextView = (TextView) findViewById(R.id.discription_count);
        overlayView = findViewById(R.id.overlay);
        titlePlaceNameTextView = (TextView) mCustomView.findViewById(R.id.place_name);
        nextDayImageView = (ImageView) mCustomView.findViewById(R.id.next_day);
        previewTextView = (ImageView) findViewById(R.id.preview);
    }


    /**
     * 가까운 관광지를 가져옴
     *
     * @param lat    위도
     * @param lon    경도
     * @param radius 반경
     */
    void setClosedDate(Float lat, Float lon, int radius) {
        final int tempRadius = radius;

        geoQuery = geoFire.queryAtLocation(new GeoLocation(lat, lon), radius);
        array = new ArrayList<PlaceInfoDto>();
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                Log.i("ohdoking-test", key + " * ");
                loadingDialog.dismiss();

                Query placeInfoQuery = placeInfoRef.child(key);
                placeInfoQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        PlaceInfoDto placeInfoDto = dataSnapshot.getValue(PlaceInfoDto.class);
                        Log.i("ohdoking", placeInfoDto.getTitle());
                        array.add(placeInfoDto);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onKeyExited(String key) {
                Log.i("ohdoking-nbo", key + " * " + tempRadius);
                loadingDialog.show();
                // 데이터가없는경우 더 넓혀서 찾아본다.
                setClosedDate(currentLat, currentLon, tempRadius * 2);
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        //화면 클리어
                        if (flingContainer.getChildCount() != 0) {
                            flingContainer.removeAllViewsInLayout();
                        }

                        //싫어하는 리스트와 이미 선택한 리스트 필터링
                        array = getFilterList(array, placeInfoDtoList);
                        array = getFilterList(array, hateList);

<<<<<<< HEAD
                        if (array.size() == 0) {
                            if (loadingDialog != null)
                                loadingDialog.show();
                            setClosedDate(currentLat, currentLon, tempRadius * 2);
=======
                        if(array.size() == 0){
                            //loadingDialog.show();   // todo 계속중지되어서주석처리했는데확인해주세여
                            setClosedDate(currentLat,currentLon, tempRadius*2);
>>>>>>> a1ae48e963e34f0ad0d8238ad0b7df013c3417df
                        }

                        myAdapter.list = array;
                        myAdapter.notifyDataSetChanged();

                        loadingDialog.dismiss();
                    }
                }, 500);
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });

    }


    void getPlaceData(Integer contentTypeId) {

        loadingDialog.show();
        laziTripperKoreanTourClient = new LaziTripperKoreanTourClient(getApplicationContext());
        laziTripperKoreanTourService = laziTripperKoreanTourClient.getLiziTripperService();
        //@TODO 국가 정보를 받아서 지역을 뿌려준다.

        Call<CommonResponse<PlaceInfoDto>> callRelionInfo;

        //contenttype이 관광지를를 가져옴
<<<<<<< HEAD
        callRelionInfo = laziTripperKoreanTourService.getPlaceInfoByCity(pageNum, page, "B", "Y", "AND", "LaziTripper", cityCode, contentTypeId);
=======
        callRelionInfo = laziTripperKoreanTourService.getPlaceInfoByCity(pageNum,page,"B","Y","AND","LaziTripper",cityCode, contentTypeId);
>>>>>>> a1ae48e963e34f0ad0d8238ad0b7df013c3417df
        callRelionInfo.enqueue(new Callback<CommonResponse<PlaceInfoDto>>() {
            @Override
            public void onResponse(Call<CommonResponse<PlaceInfoDto>> call, Response<CommonResponse<PlaceInfoDto>> response) {
                Log.e("ohdoking", response.body().getResponse().getBody().getItems().getItems().get(0).getTitle());
                array = response.body().getResponse().getBody().getItems().getItems();

                //화면 클리어
                if (flingContainer.getChildCount() != 0) {
                    flingContainer.removeAllViewsInLayout();
                }

                array = getFilterList(array, placeInfoDtoList);

                if (array.size() == 0) {
                    loadingDialog.show();
                    setClosedDate(currentLat, currentLon, radius * 2);
                }

                myAdapter.list = array;
                myAdapter.notifyDataSetChanged();

                loadingDialog.dismiss();

            }

            @Override
            public void onFailure(Call<CommonResponse<PlaceInfoDto>> call, Throwable t) {
                Log.i("ohdoking", t.getMessage());
            }
        });
    }


    public static class ViewHolder {
        public ImageView background;
        public TextView name;
        public TextView _addr;
        public TextView addr;
        public TextView _tel;
        public TextView tel;
    }

    public class MyAdapter extends BaseAdapter {

        public List<PlaceInfoDto> list;
        public Context context;

        private MyAdapter(List<PlaceInfoDto> apps, Context context) {
            this.list = apps;
            this.context = context;
        }

        public int getCount() {
            return list.size();
        }

        public PlaceInfoDto getItem(int position) {
            return list.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.chosen_place, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.background = (ImageView) rowView.findViewById(R.id.backgroundImage);
                viewHolder.name = (TextView) rowView.findViewById(R.id.name);
                viewHolder._addr = (TextView) rowView.findViewById(R.id._addr);
                viewHolder.addr = (TextView) rowView.findViewById(R.id.addr);
                viewHolder._tel = (TextView) rowView.findViewById(R.id._tel);
                viewHolder.tel = (TextView) rowView.findViewById(R.id.tel);

                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final PlaceInfoDto curItem = list.get(position);

            Glide.with(context).load(curItem.getFirstimage()).override(485, 710).centerCrop().into(viewHolder.background);
            viewHolder.name.setText(curItem.getTitle());
            viewHolder._addr.setText("ADD");
            viewHolder.addr.setText(curItem.getAddr1());
            viewHolder._tel.setText("TEL");
            viewHolder.tel.setText(curItem.getTel());

            return rowView;
        }
    }

    void renderItem() {
        myAdapter = new MyAdapter(array, ChoosePlaceActivity.this);
        flingContainer.setAdapter(myAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {

            @Override
            public void onScroll(float v) {

            }

            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                if (array.size() != 0)
                    array.remove(0);
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                //Toast.makeText(ChoosePlaceActivity.this, "싫어요", Toast.LENGTH_SHORT).show();
                animationOverlayView(false);
                PlaceInfoDto placeInfoDto = (PlaceInfoDto) dataObject;
                hateList.add(placeInfoDto);
                if (myAdapter.list.size() == 0) {
                    loadingDialog.show();
                    if (locationCount == 0) {
                        page++;
                        getPlaceData(12);
                    } else {
                        radius = radius * 2;
                        setClosedDate(currentLat, currentLon, radius);
                    }
                }
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                count++;
                locationCount++;
                //Toast.makeText(ChoosePlaceActivity.this,  locationCount + "회 좋아요", Toast.LENGTH_SHORT).show();
                animationOverlayView(true);
                PlaceInfoDto placeInfoDto = (PlaceInfoDto) dataObject;
                placeInfoDtoList.add(placeInfoDto);
                currentLat = placeInfoDto.getMapy();
                currentLon = placeInfoDto.getMapx();
                //5개이상은 선택할 수 없음 알림 메시지와 함께 다음 일정으로
                //4개를 선택하면 더 선택할것인지 물어봄
                if (locationCount.equals(5)) {
                    Toast.makeText(ChoosePlaceActivity.this, R.string.alert5below, Toast.LENGTH_LONG).show();

                    goNextActivity();
                    overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.fade_in);
                } else if (locationCount.equals(4)) {
                    showAlertDialog();
                } else {
                    radius = 1;
                    loadingDialog.show();
                    setClosedDate(placeInfoDto.getMapy(), placeInfoDto.getMapx(), radius);
                }


                thisCount++;
                discriptionCountTextView.setText(makeSentence(thisCount));

            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                myAdapter.notifyDataSetChanged();
                i++;
            }
        });

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject, View frame) {
                PlaceInfoDto infodto = (PlaceInfoDto) dataObject;
                ImageView ivChoosePlace = (ImageView) frame.findViewById(R.id.backgroundImage);
                Intent intent = new Intent(ChoosePlaceActivity.this, PlaceDetailActivity.class);
                View sharedView = ivChoosePlace;
                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(ChoosePlaceActivity.this, sharedView, getString(R.string.transitionImageName));
<<<<<<< HEAD
                intent.putExtra("placeInfo", infodto);
                startActivity(intent, transitionActivityOptions.toBundle());
=======
                intent.putExtra("placeInfo",infodto);
                startActivity(intent,transitionActivityOptions.toBundle());
                finish(); //  추가했습니당
>>>>>>> a1ae48e963e34f0ad0d8238ad0b7df013c3417df

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, R.string.back_cause,
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

    //랜드마크 사이에 음식점 삽입
    private void inputLandMarkAndLandMark(PlaceInfoDto placeInfoDto) {
        if (array.size() != count) {
            placeInfoDtoList.add(((landMarkUseCount * 2) - 1), placeInfoDto);
        } else {
            placeInfoDtoList.add(placeInfoDto);
        }

    }

    private String makeSentence(int count) {
        //int totalCount;
        //totalCount = placeCount.getAccommodation() + placeCount.getLandMark() + placeCount.getRestaurant();
        return "오른쪽으로 넘긴 여행지가 루트에 추가되요  " + count + "/5";
    }

    //지역 코드를 지역 명으로 변환해준다
    public String makeTitleName(Integer day, Integer cityCode) {

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
        return "DAY" + day + " " + name;
    }

    @Override
    public void setHeader() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        mCustomView = LayoutInflater.from(this).inflate(R.layout.choose_place_header, null);
        actionBar.setCustomView(mCustomView);

        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

//        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.facebook_btn));

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        actionBar.setCustomView(mCustomView, params);
    }

    public List getFilterList(List<PlaceInfoDto> originalList, List<PlaceInfoDto> filterList) {
        ArrayList<PlaceInfoDto> tempList = new ArrayList<PlaceInfoDto>();
        boolean hasItem;
        for (PlaceInfoDto placeInfo : originalList) {
            hasItem = false;
            for (PlaceInfoDto placeInfoDto : filterList) {
                if (placeInfoDto.getContentid().equals(placeInfo.getContentid())) {
                    hasItem = true;
                    break;
                }
            }
            if (!hasItem) {
                if (!"".equals(placeInfo.getFirstimage())) {
                    tempList.add(placeInfo);
                }
            }
        }
        return tempList;
    }

    /**
     * o,x 화면 나왓다 사라지는 애니메이션
     */
    void animationOverlayView(boolean pass) {
        if (pass) {
            overlayView.setBackgroundResource(R.drawable.pass);
        } else {
            overlayView.setBackgroundResource(R.drawable.reject);
        }
        final Animation animation = new AlphaAnimation(0, 1); // Change alpha from fully visible to invisible
        animation.setDuration(300); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(1); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
        overlayView.startAnimation(animation);
    }

    /**
     * 경고메시지
     */
    void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.warning)
                .setIcon(R.mipmap.ic_launcher)
                .setMessage(R.string.alert4below)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        radius = 3;
                        loadingDialog.show();
<<<<<<< HEAD
                        setClosedDate(currentLat, currentLat, radius);
=======
                        setClosedDate(currentLat, currentLat,radius);
>>>>>>> a1ae48e963e34f0ad0d8238ad0b7df013c3417df
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goNextActivity();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * 다음페이지로 전환
     */
    private void goNextActivity() {
        //TODO 모든 날의 일정을 다 설정하면 다음 써머리화면으로 넘어가야함(데이터를 한꺼번에 넘김?)
        // 결과 데이터를
        allTravelInfo.getAllTraveInfo().get(day - 1).setPlaceInfoDtoList(placeInfoDtoList);

        if (day.equals(totalDay)) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Gson gson = new Gson();
            String jsonString = gson.toJson(allTravelInfo);
            AllTravelInfo allTravelInfo1 = gson.fromJson(jsonString, AllTravelInfo.class);
            sharedPreferenceStore1 = new SharedPreferenceStore(getApplicationContext(), ConstantStore.STORE);
            uuid = (String) sharedPreferenceStore1.getPreferences(ConstantStore.UUID, String.class);
            //firebase 에 데이터 저장
            userInfoRef.child(uuid).child("Travel").child(String.valueOf(timestamp.getTime())).setValue(allTravelInfo1);
            Intent i = new Intent(ChoosePlaceActivity.this, TravelSummaryActivity.class);
            i.putExtra(ConstantIntent.AllTRAVELINFO, allTravelInfo);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(ChoosePlaceActivity.this, ChoosePlaceActivity.class);
            i.putExtra(ConstantIntent.AllTRAVELINFO, allTravelInfo);
            i.putExtra(ConstantIntent.CURRENTDAY, ++day);
            startActivity(i);
            finish();
        }
    }

}