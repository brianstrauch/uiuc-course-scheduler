package edu.illinois.finalproject.view;

import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import edu.illinois.finalproject.R;
import edu.illinois.finalproject.model.Course;
import edu.illinois.finalproject.model.Meeting;
import edu.illinois.finalproject.model.Schedule;
import edu.illinois.finalproject.model.Time;

public class ScheduleFragment extends Fragment {
    private View view;

    private WeekView weekView;
    private Schedule schedule;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.schedule, container, false);

        weekView = view.findViewById(R.id.wv_calendar);
        setWeekView();

        schedule = new Schedule();

        return view;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !schedule.isPossible()) {
            Toast.makeText(view.getContext(), "No possible schedules.", Toast.LENGTH_SHORT).show();
        }
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

                if(schedule.isPossible()) {
                    weekView.setVisibility(View.VISIBLE);
                } else {
                    weekView.setVisibility(View.INVISIBLE);
                    return events;
                }

                // Don't load previous or next month
                if(newMonth != Calendar.getInstance().get(Calendar.MONTH) + 1) {
                    return events;
                }

                List<Course> courses = schedule.getCourses();
                if(courses == null) {
                    return events;
                }

                for(Course course : courses) {
                    if(course.getSelectedLecture() != null) {
                        for (Meeting meeting : course.getSelectedLecture().getMeetings()) {
                            for (Time time : meeting.getTimes()) {
                                WeekViewEvent event = new WeekViewEvent(
                                        0,
                                        course.getId(),
                                        convertTime(time.getStart()),
                                        convertTime(time.getEnd())
                                );
                                event.setColor(course.getColor());
                                events.add(event);
                            }
                        }
                    }

                    if(course.getSelectedDiscussion() != null) {
                        for (Meeting meeting : course.getSelectedDiscussion().getMeetings()) {
                            for (Time time : meeting.getTimes()) {
                                WeekViewEvent event = new WeekViewEvent(
                                        0,
                                        course.getId(),
                                        convertTime(time.getStart()),
                                        convertTime(time.getEnd())
                                );
                                event.setColor(course.getColor());
                                events.add(event);
                            }
                        }
                    }

                    if(course.getSelectedLab() != null) {
                        for (Meeting meeting : course.getSelectedLab().getMeetings()) {
                            for (Time time : meeting.getTimes()) {
                                WeekViewEvent event = new WeekViewEvent(
                                        0,
                                        course.getId(),
                                        convertTime(time.getStart()),
                                        convertTime(time.getEnd())
                                );
                                event.setColor(course.getColor());
                                events.add(event);
                            }
                        }
                    }
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

    // Convert integer to calendar object
    private Calendar convertTime(int time) {
        Calendar calendar = Calendar.getInstance();

        int minute = time % 60;
        calendar.set(Calendar.MINUTE, minute);
        time /= 60;

        int hour = time % 24;
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        time /= 24;

        int day = Calendar.MONDAY + time;
        calendar.set(Calendar.DAY_OF_WEEK, day);

        return calendar;
    }

    public void setCourses(List<Course> courses) {
        schedule.setCourses(courses);

        for(Course course : courses) {
            course.sortSections();
        }

        schedule.generate();
        if(schedule.isPossible()) {
            weekView.notifyDatasetChanged();
        }
    }
}
