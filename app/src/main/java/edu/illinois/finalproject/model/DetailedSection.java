package edu.illinois.finalproject.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class DetailedSection {
    @ElementList
    private List<Meeting> meetings;

    /**
     * Determines if there is an overlap between two sections
     * @param other The section being compared to this section
     * @return If there is a schedule conflict
     */
    public boolean conflictsWith(DetailedSection other) {
        for (Meeting meeting1 : meetings) {
            for (Meeting meeting2 : other.meetings) {
                if (meeting1.conflictsWith(meeting2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }
}
