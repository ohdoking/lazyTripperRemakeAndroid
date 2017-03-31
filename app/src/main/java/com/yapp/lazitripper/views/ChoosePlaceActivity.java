package com.yapp.lazitripper.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.dto.PlaceCount;
import com.yapp.lazitripper.dto.PlaceInfoDto;
import com.yapp.lazitripper.dto.common.CommonResponse;
import com.yapp.lazitripper.network.LaziTripperKoreanTourClient;
import com.yapp.lazitripper.service.LaziTripperKoreanTourService;
import com.yapp.lazitripper.util.TravelRoute;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;
import com.yapp.lazitripper.views.dialog.LoadingDialog;

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
    int i=0;

    public MyAdapter myAdapter;
    public ViewHolder viewHolder;
    private List<PlaceInfoDto> array;
    SwipeFlingAdapterView flingContainer;

    public PlaceInfoDto placeInfoDto;
    public LaziTripperKoreanTourClient laziTripperKoreanTourClient;
    public LaziTripperKoreanTourService laziTripperKoreanTourService;
    private String TAG = "ChoosePlaceActivity";

    Integer cityCode;
    ImageView kindTextVeiw;
    TextView discriptionCountTextView;

    TextView titlePlaceNameTextView;

    LoadingDialog loadingDialog;

    /*
        기본 선택 값들

        Integer landMarkCount = 4;
        Integer restaurantCount = 3;
        Integer hotelCount = 1;
    * */
    PlaceCount placeCount;

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


    ArrayList<PlaceInfoDto> placeInfoDtoList = new ArrayList<PlaceInfoDto>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHeader();
        setContentView(R.layout.activity_choose_place);
        loadingDialog = new LoadingDialog(ChoosePlaceActivity.this);

//        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
//        setProgressBarIndeterminateVisibility(true);



        //이전 엑티비티에서 city code를 가져옴
        cityCode = getIntent().getIntExtra(ConstantIntent.CITYCODE,1);
        placeCount = (PlaceCount) getIntent().getSerializableExtra(ConstantIntent.PLACECOUNT);
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        discriptionCountTextView = (TextView) findViewById(R.id.discription_count);

        titlePlaceNameTextView = (TextView) mCustomView.findViewById(R.id.place_name);
        titlePlaceNameTextView.setText(makeTitleName(cityCode));


        array = new ArrayList<>();
        //12 관광지

        getPlaceData(12);
        discriptionCountTextView.setText(makeSentence(thisCount));
        renderItem();

    }



    void getPlaceData(Integer contentTypeId){

        loadingDialog.show();
        laziTripperKoreanTourClient = new LaziTripperKoreanTourClient(getApplicationContext());
        laziTripperKoreanTourService = laziTripperKoreanTourClient.getLiziTripperService();
        //@TODO 국가 정보를 받아서 지역을 뿌려준다.

        Call<CommonResponse<PlaceInfoDto>> callRelionInfo;


        //contenttype이 관광지면 도시 기반으로 가져오고 나머지인경우 위치에 가까운 식당이나 숙소 데이터를 가져온다.
        if(contentTypeId == 12){
            callRelionInfo = laziTripperKoreanTourService.getPlaceInfoByCity(pageNum,page,"B","Y","AND","LaziTripper",cityCode, contentTypeId);
        }
        else{
            if(landMarkUseCount >= placeCount.getLandMark()){
                landMarkUseCount = placeCount.getLandMark()-1;
            }
            else if(contentTypeId == 32){
                //숙소는 마지막 랜드마크 다음
                landMarkUseCount = travelRoute.getList().size() - 1 ;
            }
            callRelionInfo = laziTripperKoreanTourService.getPlaceInfoByLocation(pageNum,page,"E","Y","AND","LaziTripper",contentTypeId, travelRoute.getList().get(landMarkUseCount).getMapx(), travelRoute.getList().get(landMarkUseCount).getMapy() ,100000);
            if(array.size() != count){
                landMarkUseCount++;
            }
        }


        callRelionInfo.enqueue(new Callback<CommonResponse<PlaceInfoDto>>() {
            @Override
            public void onResponse(Call<CommonResponse<PlaceInfoDto>> call, Response<CommonResponse<PlaceInfoDto>> response) {
                Log.e("ohdoking",response.body().getResponse().getBody().getItems().getItems().get(0).getTitle());
                array = response.body().getResponse().getBody().getItems().getItems();

                //화면 클리어
                if(flingContainer.getChildCount() != 0){
                    flingContainer.removeAllViewsInLayout();
                }

                ArrayList<PlaceInfoDto> tempList = new ArrayList<PlaceInfoDto>();
                boolean hasItem;
                for(PlaceInfoDto placeInfo : array){
                    hasItem = false;
                    for(PlaceInfoDto placeInfoDto : placeInfoDtoList){
                        if(placeInfoDto.getContentid().equals(placeInfo.getContentid())){
                            hasItem = true;
                            break;
                        }
                    }
                    if(!hasItem){
                        tempList.add(placeInfo);
                    }
                }
                array = tempList;

                myAdapter.list = array;
                myAdapter.notifyDataSetChanged();

                loadingDialog.dismiss();

            }

            @Override
            public void onFailure(Call<CommonResponse<PlaceInfoDto>> call, Throwable t) {
                Log.i("ohdoking",t.getMessage());
            }
        });
    }


    public static class ViewHolder{
        public ImageView background;
        public TextView name;
        public ImageView image;
        public TextView _addr;
        public TextView addr;
        public TextView _tel;
        public TextView tel;
    }

    public class MyAdapter extends BaseAdapter{

        public List<PlaceInfoDto> list;
        public Context context;

        private MyAdapter(List<PlaceInfoDto> apps, Context context){
            this.list = apps;
            this.context = context;
        }

        public int getCount(){
            return list.size();
        }

        public PlaceInfoDto getItem(int position){
            return list.get(position);
        }

        public long getItemId(int position){
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent){
            View rowView = convertView;

            if(rowView == null){
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.chosen_place, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.background = (ImageView) rowView.findViewById(R.id.backgroundImage);
                viewHolder.name = (TextView) rowView.findViewById(R.id.name);
                viewHolder.image = (ImageView) rowView.findViewById(R.id.country_image);
                viewHolder._addr = (TextView) rowView.findViewById(R.id._addr);
                viewHolder.addr = (TextView) rowView.findViewById(R.id.addr);
                viewHolder._tel = (TextView) rowView.findViewById(R.id._tel);
                viewHolder.tel = (TextView) rowView.findViewById(R.id.tel);

                rowView.setTag(viewHolder);

            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            PlaceInfoDto curItem = list.get(position);
            Log.i("ohdoking",curItem.getTitle());

//            viewHolder.background.set(0xff556677);
            Glide.with(context).load(curItem.getFirstimage()).override(485, 710).centerCrop().into(viewHolder.background);
            viewHolder.name.setText(curItem.getTitle());
            viewHolder.image.setImageDrawable(getResources().getDrawable(R.drawable.korea));
            viewHolder._addr.setText("ADD");
            viewHolder.addr.setText(curItem.getAddr1());
            viewHolder._tel.setText("TEL");
            viewHolder.tel.setText(curItem.getTel());
            return rowView;
        }
    }

    void renderItem(){
        myAdapter = new MyAdapter(array,ChoosePlaceActivity.this);
        flingContainer.setAdapter(myAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {

            @Override
            public void onScroll(float v) {

            }

            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                array.remove(0);
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(ChoosePlaceActivity.this, "싫어요", Toast.LENGTH_SHORT).show();

                if(locationCount % pageNum == 0 && locationCount != 0){
                    page++;

                    if(locationFlag == 0){
                        getPlaceData(12);
                    }
                    else if(locationFlag == 1){
                        getPlaceData(39);
                    }
                    else if(locationFlag == 2){
                        getPlaceData(32);
                    }
                }
            }

            @Override
            public void onRightCardExit(Object dataObject) {

                count++;
                locationCount++;
                Toast.makeText(ChoosePlaceActivity.this,  locationCount + "회 좋아요", Toast.LENGTH_SHORT).show();
                if(locationFlag == 0){
                    placeInfoDtoList.add((PlaceInfoDto) dataObject);
                    if(placeCount.getLandMark().equals(locationCount)){

                        locationCount = 0;
                        locationFlag = 1;
                        count = 0;
                        page = 1;

                        //최단거리 구함
                        travelRoute = new TravelRoute(placeInfoDtoList);
                        placeInfoDtoList = travelRoute.findShortRoute();

                        //39 음식
                        getPlaceData(39);
                    }
                    else if(locationCount % pageNum == 0 && locationCount != 0){
                        page++;
                        getPlaceData(12);
                    }
                }
                else if(locationFlag == 1){
                    //랜드마크와 랜드마크 사이에 음식점 넣기
                    inputLandMarkAndLandMark((PlaceInfoDto) dataObject);
                    if(placeCount.getRestaurant().equals(locationCount)) {

                        locationCount = 0;
                        locationFlag = 2;
                        count = 0;
                        page = 1;

                        //32 숙박
                        getPlaceData(32);
                    }
                    else{

                        if(locationCount % pageNum == 0 && locationCount != 0){
                            page++;
                        }
                        //다음 경로의 주변 음식 가져오기
                        getPlaceData(39);
                    }

                }
                else if(locationFlag == 2){
                    placeInfoDtoList.add((PlaceInfoDto) dataObject);
                    if(placeCount.getAccommodation().equals(locationCount)){

                        locationCount = 0;
                        count = 0;
                        page = 1;
                        Intent i = new Intent(ChoosePlaceActivity.this, TravelSummaryActivity.class);
                        i.putExtra(ConstantIntent.PLACELIST,placeInfoDtoList);
                        startActivity(i);
                        finish();
                        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.fade_in);
                    }
                    else if(locationCount % pageNum == 0 && locationCount != 0){
                        page++;
                        getPlaceData(32);
                    }
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
            public void onItemClicked(int itemPosition, Object dataObject) {
                PlaceInfoDto infodto = (PlaceInfoDto) dataObject;

                Log.e(TAG,"position = " + itemPosition + ", Object" + infodto.getAddr1());
                Intent intent = new Intent(getApplicationContext(),MyProfileActivity.class);
                intent.putExtra("placeInfo",infodto);
                startActivity(intent);

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
    private void inputLandMarkAndLandMark(PlaceInfoDto placeInfoDto){
        if(array.size() != count){
            placeInfoDtoList.add(((landMarkUseCount*2)-1), placeInfoDto);
        }
        else{
            placeInfoDtoList.add(placeInfoDto);
        }

    }

    private String makeSentence(int count){
        int totalCount;
        totalCount = placeCount.getAccommodation() + placeCount.getLandMark() + placeCount.getRestaurant();
        return "오른쪽으로 넘긴 여행지가 루트에 추가되요  " + count + "/" + totalCount;
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
        return "한국 " + name;
    }

    @Override
    public void setHeader(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        mCustomView = LayoutInflater.from(this).inflate(R.layout.choose_place_header,null);
        actionBar.setCustomView(mCustomView);

        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0,0);

//        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.facebook_btn));

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        actionBar.setCustomView(mCustomView, params);
    }

}
