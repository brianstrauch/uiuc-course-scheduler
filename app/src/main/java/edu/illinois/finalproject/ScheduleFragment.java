package edu.illinois.finalproject;

import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ScheduleFragment extends Fragment {
    private WeekView weekView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule, container, false);

        weekView = view.findViewById(R.id.wv_calendar);
        setWeekView();

        return view;
    }

    private void setWeekView() {
        // Put Monday at 8AM the top left.
        Calendar monday = Calendar.getInstance();
        monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        weekView.goToDate(monday);
        weekView.goToHour(8);

        weekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {}
        });

        weekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                List<WeekViewEvent> events = new ArrayList<>();

                // Only load current month
                if(newMonth != Calendar.getInstance().get(Calendar.MONTH) + 1) {
                    return events;
                }

                return events;
            }
        });

        weekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {}
        });

        weekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                return simpleDateFormat.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                if(hour == 0) {
                    return "12 AM";
                } else if(hour < 12) {
                    return hour + " AM";
                } else if(hour == 12) {
                    return "12 PM";
                } else {
                    return (hour - 12) + " PM";
                }
            }
        });
    }
}
