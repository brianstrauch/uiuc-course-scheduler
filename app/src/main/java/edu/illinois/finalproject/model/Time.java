package edu.illinois.finalproject.model;

public class Time {
    private int start;
    private int end;

    public Time(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public boolean conflictsWith(Time other) {
        if(end < other.start) return false;
        if(other.end < start) return false;
        return true;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
