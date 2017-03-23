package com.yapp.lazitripper.views.component.weekcalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.yapp.lazitripper.dto.PickDate;

import org.joda.time.DateTime;

import noman.weekcalendar.R;

/**
 * Created by nor on 12/5/2015.
 */
public class WeekPager extends ViewPager {
    private static final String TAG = "WeekCalendar";
    private PagerAdapter adapter;
    private int pos;
    private boolean check;
    public static int NUM_OF_PAGES;
    private TypedArray typedArray;
    private TextView monthTextView;
    private TextView yearTextView;
    private PickDate pickDate;

    public WeekPager(Context context) {
        super(context);
        initialize(null);
    }

    public WeekPager(Context context, AttributeSet attrs, TextView monthTextView, TextView yearTextView, PickDate pickDate) {
        super(context, attrs);
        this.monthTextView = monthTextView;
        this.yearTextView = yearTextView;
        this.pickDate = pickDate;
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        if (attrs != null) {
            typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WeekCalendar);
            NUM_OF_PAGES = typedArray.getInt(R.styleable.WeekCalendar_numOfPages, 100);
        }
        setId(idCheck());
        initPager(new DateTime());
        BusProvider.getInstance().register(this);

    }

    private void initPager(DateTime dateTime) {
        pos = NUM_OF_PAGES / 2;
        adapter = new PagerAdapter(getContext(), ((AppCompatActivity) getContext())
                .getSupportFragmentManager(), dateTime, typedArray, pickDate);
        setAdapter(adapter);
        addOnPageChangeListener(new ViewPager
                .SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (!check){
                    if (position < pos){
                        adapter.swipeBack();
                    }
                    else if (position > pos){
                        adapter.swipeForward();
                    }
                }
                pos = position;
                check = false;

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == 0 ){
//                    yearTextView.setText(getYear());
                    monthTextView.setText(getMonth()+"");
                }
            }



        });
        setOverScrollMode(OVER_SCROLL_NEVER);
        setCurrentItem(pos);
        if (typedArray != null)
            setBackgroundColor(typedArray.getColor(R.styleable.WeekCalendar_daysBackgroundColor,
                    ContextCompat.getColor(getContext(), R.color.colorPrimary)));
        if (WeekFragment.selectedDateTime == null)
            WeekFragment.selectedDateTime = new DateTime();
    }

    @Subscribe
    public void setCurrentPage(Event.SetCurrentPageEvent event) {
        check = true;
        if (event.getDirection() == 1)
            adapter.swipeForward();
        else
            adapter.swipeBack();
        setCurrentItem(getCurrentItem() + event.getDirection());

    }

    @Subscribe
    public void reset(Event.ResetEvent event) {
        WeekFragment.selectedDateTime = new DateTime(WeekFragment.CalendarStartDate);
        //WeekFragment.CalendarStartDate = new DateTime();
        initPager(WeekFragment.CalendarStartDate);
    }

    @Subscribe
    public void setSelectedDate(Event.SetSelectedDateEvent event) {
        WeekFragment.selectedDateTime = event.getSelectedDate();
        initPager(event.getSelectedDate());
    }

    @Subscribe
    public void setStartDate(Event.SetStartDateEvent event) {
        WeekFragment.CalendarStartDate = event.getStartDate();
        WeekFragment.selectedDateTime = event.getStartDate();
        initPager(event.getStartDate());
    }

    public Integer getMonth(){
        return adapter.getMonth();
    }

    public Integer getYear(){
        return adapter.getYear();
    }



    private int idCheck() {

        int id = 0;
        while (findViewById(++id) != null) ;
        return id;
    }
}
