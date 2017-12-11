package edu.illinois.finalproject.model;

import android.os.Parcel;
import android.os.Parcelable;

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
public class Course implements Parcelable {
    @Attribute
    private String id;
    @Element
    private String label;
    @Element
    private String description;
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
    public Course() {
        this.color = COLORS.get(count);
        // Cycle through colors
        count = (count + 1) % COLORS.size();
    }

    // Constructor used when user adds a class
    public Course(String department, String number) {
        buildURL(department, number);
    }

    /**
     * Sets the URL used to download course data from the API
     * @param department The course department (Ex: 'CS')
     * @param number The course number (Ex: '126')
     */
    private void buildURL(String department, String number) {
        String url = String.format(
                "https://courses.illinois.edu/cisapp/explorer/schedule/2018/spring/%s/%s.xml?mode=detail",
                department,
                number
        );

        try {
            requestURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    protected Course(Parcel in) {
        id = in.readString();
        label = in.readString();
        description = in.readString();
        color = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(label);
        dest.writeString(description);
        dest.writeInt(color);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    /**
     * Group a course's sections by lectures, discussions, and labs
     */
    public void sortSections() {
        lectures = new ArrayList<>();
        discussions = new ArrayList<>();
        labs = new ArrayList<>();

        for(DetailedSection section : detailedSections) {
            for(Meeting meeting : section.getMeetings()) {
                meeting.init();
            }

            Meeting firstListedMeeting = section.getMeetings().get(0);
            Type type = firstListedMeeting.getType();

            // Uses fallthrough technique
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
                case "PKG": // Package
                    break;
                default:
                    break;
            }
        }
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
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
