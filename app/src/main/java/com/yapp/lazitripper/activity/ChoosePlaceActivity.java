package com.yapp.lazitripper.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.dto.PlaceInfoDto;
import com.yapp.lazitripper.dto.common.CommonItems;
import com.yapp.lazitripper.dto.RegionCodeDto;
import com.yapp.lazitripper.dto.common.CommonResponse;
import com.yapp.lazitripper.network.LaziTripperKoreanTourClient;
import com.yapp.lazitripper.service.LaziTripperKoreanTourService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChoosePlaceActivity extends AppCompatActivity {

    //private ArrayList<ChosenPlaceItem> a;
    private ArrayList<String> b;
    private ArrayAdapter<String> arrayAdapter;
    //private ChosenPlaceAdapter adapter;
    int i=0;

    public MyAdapter myAdapter;
    public static ViewHolder viewHolder;
    private List<PlaceInfoDto> array;
    SwipeFlingAdapterView flingContainer;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public PlaceInfoDto placeInfoDto;
    public LaziTripperKoreanTourClient laziTripperKoreanTourClient;
    public LaziTripperKoreanTourService laziTripperKoreanTourService;

    Integer cityCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_place);

        pref = getPreferences(MODE_PRIVATE);
        editor = pref.edit();

        //이전 엑티비티에서 city code를 가져옴
        cityCode = getIntent().getIntExtra(ConstantIntent.CITYCODE,1);
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        array = new ArrayList<>();
        getCityData();
        renderItem();

    }

    void getCityData(){
        laziTripperKoreanTourClient = new LaziTripperKoreanTourClient(getApplicationContext());
        laziTripperKoreanTourService = laziTripperKoreanTourClient.getLiziTripperService();
        //@TODO 국가 정보를 받아서 지역을 뿌려준다.
        Call<CommonResponse<PlaceInfoDto>> callRelionInfo = laziTripperKoreanTourService.getPlaceInfoByCity(20,1,"B","Y","AND","LaziTripper",cityCode);

        callRelionInfo.enqueue(new Callback<CommonResponse<PlaceInfoDto>>() {
            @Override
            public void onResponse(Call<CommonResponse<PlaceInfoDto>> call, Response<CommonResponse<PlaceInfoDto>> response) {
                Log.i("ohdoking",response.body().getResponse().getBody().getItems().getItems().get(0).getTitle());
                array = response.body().getResponse().getBody().getItems().getItems();
                myAdapter.list = array;
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CommonResponse<PlaceInfoDto>> call, Throwable t) {
                Log.i("ohdoking",t.getMessage());
            }
        });
    }


    public static class ViewHolder{
        public static FrameLayout background;
        public ImageView image;
        public TextView name;
        public TextView description;
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

        public Object getItem(int position){
            return position;
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
                viewHolder.image = (ImageView) rowView.findViewById(R.id.image);
                viewHolder.name = (TextView) rowView.findViewById(R.id.name);
                viewHolder.description = (TextView) rowView.findViewById(R.id.description);
                rowView.setTag(viewHolder);

            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            PlaceInfoDto curItem = list.get(position);
            Log.i("ohdoking",curItem.getTitle());
            Glide.with(context).load(curItem.getFirstimage()).into(viewHolder.image);
            viewHolder.name.setText(curItem.getTitle());
            viewHolder.description.setText(curItem.getTel());

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
                Toast.makeText(ChoosePlaceActivity.this, "Left!", Toast.LENGTH_SHORT).show();
                editor.putString("ID","아이디~!~");
                editor.commit();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(ChoosePlaceActivity.this, "Right!", Toast.LENGTH_SHORT).show();
                String s = pref.getString("ID","없음");
                Log.d("test",s+"ddddddddddddd");
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
