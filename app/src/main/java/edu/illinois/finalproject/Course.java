package edu.illinois.finalproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Course {
    private String id;

    private static final List<Integer> COLORS = new ArrayList<Integer>() {
        {
            add(0xff1abc9c);
            add(0xff2ecc71);
            add(0xff3498db);
            add(0xff9b59b6);
            add(0xfff1c40f);
            add(0xffe67e22);
            add(0xffe74c3c);

            Collections.shuffle(this);
        }
    };

    private int color;
    private static int count = 0;

    public Course(String department, int number) {
        // Replaces XML request
        id = String.format("%s %d", department, number);

        this.color = COLORS.get(count);
        // Cycle through colors
        count = (count + 1) % COLORS.size();
    }

    public String getId() {
        return id;
    }
}
