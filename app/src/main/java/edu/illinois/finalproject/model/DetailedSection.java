package edu.illinois.finalproject.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class DetailedSection {
    @Element
    private String enrollmentStatus;
    @ElementList
    private List<Meeting> meetings;


    public boolean conflictsWith(DetailedSection other) {
        for (Meeting meeting1 : meetings) {
            for (Meeting meeting2 : other.meetings) {
                if (meeting1.conflictsWith(meeting2)) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }
}
