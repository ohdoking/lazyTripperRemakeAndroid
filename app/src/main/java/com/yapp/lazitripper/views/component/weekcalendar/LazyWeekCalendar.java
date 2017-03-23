package com.yapp.lazitripper.views.component.weekcalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.yapp.lazitripper.dto.PickDate;

import org.joda.time.DateTime;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ohdoking on 2017. 3. 20..
 */

public class LazyWeekCalendar extends LinearLayout {
    private static final String TAG = "WeekCalendarTAG";
    private OnDateClickListener listener;
    private TypedArray typedArray;
    private GridView daysName;
    private TextView monthTextView;
    private TextView yearTextView;
    private WeekPager weekPager;
    private PickDate pickDate;


    public LazyWeekCalendar(Context context) {
        super(context);
        init(null);
    }

    public LazyWeekCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public LazyWeekCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            typedArray = getContext().obtainStyledAttributes(attrs, noman.weekcalendar.R.styleable.WeekCalendar);
        }

        pickDate = new PickDate();
        setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT));

        linearLayout.setOrientation(LinearLayout.VERTICAL);

        monthTextView = new TextView(getContext());
        yearTextView = new TextView(getContext());

        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        monthTextView.setText(month+"");
        monthTextView.setGravity(Gravity.CENTER);
        monthTextView.setTextSize(40);
        yearTextView.setText("2017");
        LinearLayout weekPagerLayout = new LinearLayout(getContext());
        weekPagerLayout.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        weekPagerLayout.setOrientation(LinearLayout.VERTICAL);

        if (!typedArray.getBoolean(noman.weekcalendar.R.styleable.WeekCalendar_hideNames, false)) {
            daysName = getDaysNames();
            weekPagerLayout.addView(daysName, 0);
        }
        weekPager = new WeekPager(getContext(), attrs, monthTextView, yearTextView, pickDate);
        weekPagerLayout.addView(weekPager);

        linearLayout.addView(yearTextView);
        linearLayout.addView(monthTextView);
        addView(linearLayout);
        addView(weekPagerLayout);
        BusProvider.getInstance().register(this);

    }

    /***
     * Do not use this method
     * this is for receiving date,
     * use "setOndateClick" instead.
     */
    @Subscribe
    public void onDateClick(Event.OnDateClickEvent event) {
        if (listener != null)
            listener.onDateClick(event.getDateTime());
    }

    public void setOnDateClickListener(OnDateClickListener listener) {
        this.listener = listener;
    }


    private GridView getDaysNames() {
        daysName = new GridView(getContext());
        daysName.setSelector(new StateListDrawable());
        daysName.setNumColumns(7);

        daysName.setAdapter(new BaseAdapter() {
            private String[] days = getWeekDayNames();

            public int getCount() {
                return days.length;
            }

            @Override
            public String getItem(int position) {
                return days[position];
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(noman.weekcalendar.R.layout.week_day_grid_item, null);
                }
                TextView day = (TextView) convertView.findViewById(noman.weekcalendar.R.id.daytext);
                day.setText(days[position]);
                if (typedArray != null) {
                    day.setTextColor(typedArray.getColor(noman.weekcalendar.R.styleable.WeekCalendar_weekTextColor,
                            Color.WHITE));
                    day.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimension(noman.weekcalendar.R.styleable
                            .WeekCalendar_weekTextSize, day.getTextSize()));
                }
                return convertView;
            }

            private String[] getWeekDayNames() {
                String[] names = DateFormatSymbols.getInstance().getShortWeekdays();
                List<String> daysName = new ArrayList<>(Arrays.asList(names));
                daysName.remove(0);
                daysName.add(daysName.remove(0));

                if (typedArray.getInt(noman.weekcalendar.R.styleable.WeekCalendar_dayNameLength, 0) == 0)
                    for (int i = 0; i < daysName.size(); i++)
                        daysName.set(i, daysName.get(i).substring(0, 1));
                names = new String[daysName.size()];
                daysName.toArray(names);
                return names;

            }
        });
        if (typedArray != null)
            daysName.setBackgroundColor(typedArray.getColor(noman.weekcalendar.R.styleable
                    .WeekCalendar_weekBackgroundColor, ContextCompat.getColor(getContext(), noman.weekcalendar.R
                    .color.colorPrimary)));
        return daysName;
    }

    public void moveToPrevious() {
        BusProvider.getInstance().post(new Event.UpdateSelectedDateEvent(-1));
    }

    public void moveToNext() {
        BusProvider.getInstance().post(new Event.UpdateSelectedDateEvent(1));
    }

    public void reset() {
        BusProvider.getInstance().post(new Event.ResetEvent());
    }

    public void setSelectedDate(DateTime selectedDate){
        BusProvider.getInstance().post(new Event.SetSelectedDateEvent(selectedDate));
    }
    public void setStartDate(DateTime startDate){
        BusProvider.getInstance().post(new Event.SetStartDateEvent(startDate));
    }

    public void setPeriodDate(PickDate pickDate){
        this.pickDate = pickDate;
    }
}