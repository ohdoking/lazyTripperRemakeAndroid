package com.yapp.lazitripper.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

    Integer cityCode;
    ImageView kindTextVeiw;

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

    //이미 선택된 랜드마크 카운트?
    Integer landMarkUseCount = 0;

    TravelRoute travelRoute;

    ArrayList<PlaceInfoDto> placeInfoDtoList = new ArrayList<PlaceInfoDto>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHeader();
        setContentView(R.layout.activity_choose_place);
        loadingDialog = new LoadingDialog(ChoosePlaceActivity.this);

//        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
//        setProgressBarIndeterminateVisibility(true);

        ImageView leftImage = getLeftImageView();
        leftImage.setImageResource(R.drawable.map_icon);
        leftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        ImageView rightImage = getRightImageView();
        rightImage.setImageResource(R.drawable.more);
        rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChoosePlaceActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        //이전 엑티비티에서 city code를 가져옴
        cityCode = getIntent().getIntExtra(ConstantIntent.CITYCODE,1);
        placeCount = (PlaceCount) getIntent().getSerializableExtra(ConstantIntent.PLACECOUNT);
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        array = new ArrayList<>();
        //12 관광지


        getPlaceData(12);


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
            callRelionInfo = laziTripperKoreanTourService.getPlaceInfoByCity(20,1,"B","Y","AND","LaziTripper",cityCode, contentTypeId);
        }
        else{
            if(landMarkUseCount > placeCount.getLandMark()){
                landMarkUseCount = placeCount.getLandMark();
            }
            callRelionInfo = laziTripperKoreanTourService.getPlaceInfoByLocation(20,1,"E","Y","AND","LaziTripper",contentTypeId, travelRoute.getList().get(landMarkUseCount).getMapx(), travelRoute.getList().get(landMarkUseCount).getMapy() ,100000);
            landMarkUseCount++;
        }


        callRelionInfo.enqueue(new Callback<CommonResponse<PlaceInfoDto>>() {
            @Override
            public void onResponse(Call<CommonResponse<PlaceInfoDto>> call, Response<CommonResponse<PlaceInfoDto>> response) {
                Log.i("ohdoking",response.body().getResponse().getBody().getItems().getItems().get(0).getTitle());
                array = response.body().getResponse().getBody().getItems().getItems();

                //화면 클리어
                if(flingContainer.getChildCount() != 0){
                    flingContainer.removeAllViewsInLayout();
                }
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
//            Glide.with(context).load(curItem.getFirstimage()).into(viewHolder.image);

//            viewHolder.background.set(0xff556677);
            Glide.with(context).load(curItem.getFirstimage()).override(300, 700).fitCenter().into(viewHolder.background);
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
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                placeInfoDtoList.add((PlaceInfoDto) dataObject);
                count++;
                locationCount++;
                Toast.makeText(ChoosePlaceActivity.this,  locationCount + "회 좋아요", Toast.LENGTH_SHORT).show();
                if(locationFlag == 0){
                    if(placeCount.getLandMark().equals(locationCount)|| array.size() == count){
                        locationCount = 0;
                        locationFlag = 1;
                        count = 0;

                        travelRoute = new TravelRoute(array);
                        //39 음식
                        getPlaceData(39);
                    }
                }
                else if(locationFlag == 1){
                    if(placeCount.getRestaurant().equals(locationCount) || array.size() == count) {
                        locationCount = 0;
                        locationFlag = 2;
                        count = 0;
                        //32 숙박
                        getPlaceData(32);
                    }
                    else{
                        //다음 경로의 주변 음식 가져오기
                        getPlaceData(39);
                    }

                }
                else if(locationFlag == 2){
                    if(placeCount.getAccommodation().equals(locationCount) || array.size() == count){
                        locationCount = 0;
                        count = 0;
                        Intent i = new Intent(ChoosePlaceActivity.this, TravelSummaryActivity.class);
                        i.putExtra(ConstantIntent.PLACELIST,placeInfoDtoList);
                        startActivity(i);
                        finish();
                    }
                }

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
                Toast.makeText(getApplicationContext(),"hihi"+itemPosition,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(),MyProfileActivity.class);
                startActivity(intent);

            }
        });
    }

}
