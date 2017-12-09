package edu.illinois.finalproject.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.illinois.finalproject.model.Course;
import edu.illinois.finalproject.controller.CourseAdapter;
import edu.illinois.finalproject.R;

public class CourseListFragment extends Fragment implements CourseDialog.CourseDialogListener {
    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;

    private List<Course> courses;
    private DownloadCourseTask task;

    private FloatingActionButton addButton;

    private CourseListListener listener;
    public interface CourseListListener {
        void setCourses(List<Course> courses);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_list, container, false);

        courses = new ArrayList<>();
        listener = (CourseListListener) getActivity();

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
        addCourseDialog.show(getFragmentManager(), "add course");
    }

    @Override
    public void addCourse(Course course) {
        task = new DownloadCourseTask();
        task.execute(course.getRequestURL());
    }

    private class DownloadCourseTask extends AsyncTask<URL, Integer, Course> {
        @Override
        protected Course doInBackground(URL... urls) {
            try {
                InputStream input = urls[0].openStream();
                Serializer serializer = new Persister();
                return serializer.read(Course.class, input);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Course course) {
            System.out.println("Course: "+course);

            courses.add(course);
            listener.setCourses(courses);

            int end = courses.size() - 1;
            courseAdapter.notifyItemInserted(end);
            recyclerView.smoothScrollToPosition(end);
        }
    }
}
