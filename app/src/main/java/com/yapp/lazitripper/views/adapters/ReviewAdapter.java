package com.yapp.lazitripper.views.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.Travel;

import java.util.ArrayList;

/**
 * Created by ESENS on 2017-03-25.
 */

public class ReviewAdapter extends BaseAdapter {
    private ArrayList<Travel> listViewItemList = new ArrayList<Travel>() ;

    // ListViewAdapter의 생성자
    public ReviewAdapter(){};

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        //ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView2) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Travel listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        //iconImageView.setImageDrawable(listViewItem.getIcon());
        if(listViewItem.getReviewList().size() > 1){
            for(int i=0; i<listViewItem.getReviewList().size(); i++){
                titleTextView.setText(listViewItem.getReviewList().get(i).getComment());
                descTextView.setText(listViewItem.getReviewList().get(i).getUsername() + "님이 작성하셨습니다.");
            }
        }else {
            titleTextView.setText(listViewItem.getReview().getComment());
            descTextView.setText(listViewItem.getReview().getUsername() + "님이 작성하셨습니다.");
        }
        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수.
    public void addItem(Travel travel) {
        listViewItemList.add(travel);
    }
}
