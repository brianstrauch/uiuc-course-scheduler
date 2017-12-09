package edu.illinois.finalproject.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Root(strict = false)
public class Meeting {
    private static final Map<Character, Integer> DAY_ID = new HashMap<Character, Integer>() {
        {
            put('M', 0);
            put('T', 1);
            put('W', 2);
            put('R', 3);
            put('F', 4);
        }
    };

    @Element
    private Type type;
    @Element
    private String start;
    @Element
    private String end;
    @Element
    private String daysOfTheWeek;

    private int[] days;
    private Time[] times;

    // Convert days and start / end time into integers representing
    // the number of minutes since 12 AM on Monday.
    public void init() {
        if(daysOfTheWeek != null) {
            // API always gives a length 7 string.
            daysOfTheWeek = daysOfTheWeek.trim();
            parseDays();
        }

        int startTime = parseTime(start);
        int endTime = parseTime(end);

        times = new Time[days.length];
        for(int i = 0; i < days.length; i++) {
            int baseTime = days[i] * 24;
            times[i] = new Time(baseTime + startTime, baseTime + endTime);
        }
    }

    private void parseDays() {
        days = new int[daysOfTheWeek.length()];
        for(int i = 0; i < days.length; i++) {
            char day = daysOfTheWeek.charAt(i);
            int dayId = DAY_ID.get(day);
            days[i] = dayId;
        }
    }

    private int parseTime(String time) {
        if (!validTime(time)) {
            return -1;
        }

        // Divide HH:MM into hours and minutes
        String[] timeData = time.substring(0, 5).split(":");
        int hours = Integer.parseInt(timeData[0]);
        int minutes = Integer.parseInt(timeData[1]);

        // Adjust hour based on AM or PM
        String period = time.substring(time.length() - 2);
        if (hours == 12 && period.equals("AM")) {
            hours -= 12;
        } else if (hours < 12 && period.equals("PM")) {
            hours += 12;
        }

        return 60 * hours + minutes;
    }

    private boolean validTime(String time) {
        return Pattern.matches("\\d\\d:\\d\\d (PM|AM)", time);
    }

    public boolean conflictsWith(Meeting other) {
        for(Time time1 : times) {
            for(Time time2 : other.times) {
                if(time1.conflictsWith(time2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Type getType() {
        return type;
    }

    public Time[] getTimes() {
        return times;
    }
}
