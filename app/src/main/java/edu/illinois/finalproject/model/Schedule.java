package edu.illinois.finalproject.model;

import java.util.List;

public class Schedule {
    private List<Course> courses;
    private boolean possible;

    public Schedule() {
        possible = true;
    }

    public void generate() {
        System.out.println("Generating...");
        possible = generateHelper(0);
        System.out.println("Schedule possible? " + possible);
    }

    public boolean generateHelper(int i) {
        if(i == courses.size()) {
            return true;
        }
        Course course = courses.get(i);

        // Lecture
        if (course.getLectures().size() > 0) {
            boolean success = false;
            for (DetailedSection lecture : course.getLectures()) {
                if (canBeAdded(lecture)) {
                    course.setSelectedLecture(lecture);
                    success = true;
                    break;
                }
            }
            if(!success) {
                return false;
            }
        }

        // Discussion
        if (course.getDiscussions().size() > 0) {
            boolean success = false;
            for (DetailedSection discussion : course.getDiscussions()) {
                if (canBeAdded(discussion)) {
                    course.setSelectedDiscussion(discussion);
                    success = true;
                    break;
                }
            }
            if(!success) {
                return false;
            }
        }

        // Lab
        if (course.getLabs().size() > 0) {
            boolean success = false;
            for (DetailedSection lab : course.getLabs()) {
                if (canBeAdded(lab)) {
                    course.setSelectedLab(lab);
                    success = true;
                    break;
                }
            }
            if(!success) {
                return false;
            }
        }

        return generateHelper(i + 1);
    }

    private boolean canBeAdded(DetailedSection newSection) {
        for (Course course : courses) {
            // Lecture
            if (course.getSelectedLecture() != null) {
                if (newSection.conflictsWith(course.getSelectedLecture())) {
                    return false;
                }
            }

            // Discussion
            if (course.getSelectedDiscussion() != null) {
                if (newSection.conflictsWith(course.getSelectedDiscussion())) {
                    return false;
                }
            }

            // Lab
            if (course.getSelectedLab() != null) {
                if (newSection.conflictsWith(course.getSelectedLab())) {
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

    public boolean isPossible() {
        return possible;
    }
}
