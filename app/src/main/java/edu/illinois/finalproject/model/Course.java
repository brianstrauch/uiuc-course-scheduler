package edu.illinois.finalproject.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Root(strict = false)
@Namespace(prefix = "ns2")
public class Course {
    // TODO: Implement parcelable

    @Attribute
    private String id;
    @Element
    private String label;
    @ElementList
    private List<DetailedSection> detailedSections;

    private URL requestURL;

    private ArrayList<DetailedSection> lectures;
    private DetailedSection selectedLecture;

    private ArrayList<DetailedSection> discussions;
    private DetailedSection selectedDiscussion;

    private ArrayList<DetailedSection> labs;
    private DetailedSection selectedLab;

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

    // Default constructor used by SimpleXML
    public Course() {}

    public Course(String department, int number) {
        String url = String.format("https://courses.illinois.edu/cisapp/explorer/schedule/2018/spring/%s/%d.xml?mode=detail", department, number);
        try {
            requestURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.color = COLORS.get(count);
        // Cycle through colors
        count = (count + 1) % COLORS.size();
    }

    // Group sections by lectures and discussions / labs
    public void sortSections() {
        lectures = new ArrayList<>();
        discussions = new ArrayList<>();
        labs = new ArrayList<>();

        for(DetailedSection section : detailedSections) {
            // TODO: Closed sections

            // TODO: Remove
            for(Meeting meeting : section.getMeetings()) {
                meeting.init();
            }

            Meeting firstListedMeeting = section.getMeetings().get(0);
            Type type = firstListedMeeting.getType();
            switch(type.getCode()) {
                case "LCD": // Lecture Discussion
                case "LEC": // Lecture
                    lectures.add(section);
                    break;
                case "CNF": // Conference
                case "DIS": // Discussion
                case "LBD": // Lab & Discussion
                    discussions.add(section);
                    break;
                case "LAB": // Lab
                    labs.add(section);
                    break;
                case "ONL": // Online
                case "PKG": // ?
                    break;
                default:
                    System.out.println("Unknown class type: " + type.getCode());
                    break;
            }
        }
    }

    public String getId() {
        return id;
    }

    public URL getRequestURL() {
        return requestURL;
    }

    public ArrayList<DetailedSection> getLectures() {
        return lectures;
    }

    public DetailedSection getSelectedLecture() {
        return selectedLecture;
    }

    public void setSelectedLecture(DetailedSection selectedLecture) {
        this.selectedLecture = selectedLecture;
    }

    public ArrayList<DetailedSection> getDiscussions() {
        return discussions;
    }

    public DetailedSection getSelectedDiscussion() {
        return selectedDiscussion;
    }

    public void setSelectedDiscussion(DetailedSection selectedDiscussion) {
        this.selectedDiscussion = selectedDiscussion;
    }

    public ArrayList<DetailedSection> getLabs() {
        return labs;
    }

    public DetailedSection getSelectedLab() {
        return selectedLab;
    }

    public void setSelectedLab(DetailedSection selectedLab) {
        this.selectedLab = selectedLab;
    }

    public int getColor() {
        return color;
    }
}
