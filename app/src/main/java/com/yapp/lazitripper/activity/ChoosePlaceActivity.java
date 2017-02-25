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

import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.yapp.lazitripper.R;

import java.util.ArrayList;
import java.util.List;

public class ChoosePlaceActivity extends AppCompatActivity {

    //private ArrayList<ChosenPlaceItem> a;
    private ArrayList<String> b;
    private ArrayAdapter<String> arrayAdapter;
    //private ChosenPlaceAdapter adapter;
    int i=0;

    public static MyAdapter myAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<ChosenPlaceItem> array;
    SwipeFlingAdapterView flingContainer;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_place);

        pref = getPreferences(MODE_PRIVATE);
        editor = pref.edit();

        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

//        a = new ArrayList<ChosenPlaceItem>();
//        a.add(new ChosenPlaceItem(getResources().getDrawable(R.drawable.mario),"이진호","잠이오냐?"));
//
//        adapter = new ChosenPlaceAdapter(this, R.layout.chosen_place, R.id.text, a);

//        b = new ArrayList<String>();
//        b.add("sadfs");
//        b.add("qwer");
//
//        arrayAdapter = new ArrayAdapter<String>(this,R.layout.chosen_place,R.id.text,b);

        array = new ArrayList<>();
        array.add(new ChosenPlaceItem(getResources().getDrawable(R.drawable.mario),"갓도근","진호야 일어나"));
        array.add(new ChosenPlaceItem(getResources().getDrawable(R.drawable.ruigi),"이진호","예 형 일어날게여"));
        array.add(new ChosenPlaceItem(getResources().getDrawable(R.drawable.mario),"갓희원","진호야 일하자"));
        array.add(new ChosenPlaceItem(getResources().getDrawable(R.drawable.mario),"이진호","예 형 시작할게여"));


        myAdapter = new MyAdapter(array,ChoosePlaceActivity.this);
        flingContainer.setAdapter(myAdapter);
        //flingContainer.setAdapter(adapter);
        //flingContainer.setAdapter(arrayAdapter);
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
                //b.remove(0);
                //a.remove(0);
                //arrayAdapter.notifyDataSetChanged();
                //adapter.notifyDataSetChanged();

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
                // Ask for more data here
                //s.add("XML ".concat(String.valueOf(i)));
                //places.add("하이하이");

                myAdapter.notifyDataSetChanged();
                //arrayAdapter.notifyDataSetChanged();
                //adapter.notifyDataSetChanged();

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


    public static class ViewHolder{
        public static FrameLayout background;
        public ImageView image;
        public TextView name;
        public TextView description;
    }

    public class MyAdapter extends BaseAdapter{

        public List<ChosenPlaceItem> list;
        public Context context;

        private MyAdapter(List<ChosenPlaceItem> apps, Context context){
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
            ChosenPlaceItem curItem = list.get(position);

            viewHolder.image.setImageDrawable(curItem.getImage());
            viewHolder.name.setText(curItem.getName());
            viewHolder.description.setText(curItem.getDescription());
            //Glide.with(ChoosePlaceActivity.this).load(list.get(position).getImage());

            return rowView;
        }


    }

/*
    class ChosenPlaceAdapter extends BaseAdapter {


        List<ChosenPlaceItem> mItems = new ArrayList<ChosenPlaceItem>();

        public ChosenPlaceAdapter(Context context,int resourceId, ArrayList<ChosenPlaceItem> items){
            super(context,resourceId,items);
            mItems = items;
        }

        public ChosenPlaceAdapter(Context context, int resource, int textViewResourceId, List objects) {
            super(context, resource, textViewResourceId, objects);
            mItems = objects;
        }

        public void addItem(ChosenPlaceItem item){
            mItems.add(item);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ChosenPlaceView itemView = null;
            View v = convertView;

            if(v == null){
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.chosen_place_item,null);
            }

            ChosenPlaceItem curItem = mItems.get(position);

            if(curItem != null){
                ImageView image = (ImageView) v.findViewById(R.id.image);
                TextView name = (TextView) v.findViewById(R.id.name);
                TextView description = (TextView) v.findViewById(R.id.description);

            }


            return v;
        }



    }*/


}
