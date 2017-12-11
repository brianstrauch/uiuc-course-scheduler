package edu.illinois.finalproject.model;

import java.util.List;

public class Schedule {
    private List<Course> courses;
    private boolean possible;

    public Schedule() {
        possible = true;
    }

    /**
     * Greedily picks lectures, discussions, and labs, one course at a time.
     */
    public void generate() {
        for(Course course : courses) {
            if(!setLecture(course)) {
                possible = false;
                return;
            }
        }
        possible = true;
    }

    /**
     * Greedily picks a lecture, and if successful, goes on to pick a discussion
     * @param course The course providing lectures
     * @return If a lecture was successfully added to the schedule.
     */
    private boolean setLecture(Course course) {
        if (course.getLectures().size() > 0) {
            for (DetailedSection lecture : course.getLectures()) {
                if (canBeAdded(lecture)) {
                    course.setSelectedLecture(lecture);
                    if(setDiscussion(course)) {
                        return true;
                    }
                }
            }
            return false;
        }
        return setDiscussion(course);
    }

    /**
     * Greedily picks a discussion, and if successful, goes on to pick a lab
     * @param course The course providing discussions
     * @return If a discussion was successfully added to the schedule.
     */
    private boolean setDiscussion(Course course) {
        if (course.getDiscussions().size() > 0) {
            for (DetailedSection discussion : course.getDiscussions()) {
                if (canBeAdded(discussion)) {
                    course.setSelectedDiscussion(discussion);
                    if(setLab(course)) {
                        return true;
                    }
                }
            }
            return false;
        }
        return setLab(course);
    }

    /**
     * Greedily picks a lab
     * @param course The course providing labs
     * @return If a lab was successfully added to the schedule.
     */
    private boolean setLab(Course course) {
        if (course.getLabs().size() > 0) {
            for (DetailedSection lab : course.getLabs()) {
                if (canBeAdded(lab)) {
                    course.setSelectedLab(lab);
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Attempts to add a section of a course to the schedule, by checking for conflicts against
     * all other courses that have been added.
     * @param newSection The section that is being added
     * @return If the section was fit successfully
     */
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
