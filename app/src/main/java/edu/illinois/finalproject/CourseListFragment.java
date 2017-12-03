package edu.illinois.finalproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CourseListFragment extends Fragment {
    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;

    private List<Course> courses;

    private FloatingActionButton addButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_list, container, false);

        courses = new ArrayList<>();

        recyclerView = view.findViewById(R.id.rv_courses);
        setRecyclerView();

        addButton = view.findViewById(R.id.fab_add);
        setAddButton();

        return view;
    }

    private void setRecyclerView() {
        // Adapter
        courseAdapter = new CourseAdapter(courses);
        recyclerView.setAdapter(courseAdapter);

        // Layout Manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setAddButton() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        CourseDialog addCourseDialog = new CourseDialog();
        addCourseDialog.setTargetFragment(this, 0);
        addCourseDialog.show(getFragmentManager(), "add course_item");
    }
}
