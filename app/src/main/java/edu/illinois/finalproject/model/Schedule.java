package edu.illinois.finalproject.model;

import java.util.List;

public class Schedule {
    private List<Course> courses;

    public void generate() {
        for (Course course : courses) {
            course.sortSections();

            // Lecture
            if (course.getLectures().size() > 0) {
                for (DetailedSection lecture : course.getLectures()) {
                    if (canBeAdded(lecture)) {
                        course.setSelectedLecture(lecture);
                        break;
                    }
                }
            }

            // Discussion
            if (course.getDiscussions().size() > 0) {
                for (DetailedSection discussion : course.getDiscussions()) {
                    if (canBeAdded(discussion)) {
                        course.setSelectedDiscussion(discussion);
                        break;
                    }
                }
            }

            // Lab
            if (course.getLabs().size() > 0) {
                for (DetailedSection lab : course.getLabs()) {
                    if (canBeAdded(lab)) {
                        course.setSelectedLab(lab);
                        break;
                    }
                }
            }
        }
    }

    private boolean canBeAdded(DetailedSection newSection) {
        for (Course course : courses) {
            // Lecture
            if (course.getSelectedLecture() != null) {
                if(newSection.conflictsWith(course.getSelectedLecture())) {
                    return false;
                }
            }

            // Discussion
            if (course.getSelectedDiscussion() != null) {
                if(newSection.conflictsWith(course.getSelectedDiscussion())) {
                    return false;
                }
            }

            // Lab
            if (course.getSelectedLab() != null) {
                if(newSection.conflictsWith(course.getSelectedLab())) {
                    return false;
                }
            }
        }

        return true;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
