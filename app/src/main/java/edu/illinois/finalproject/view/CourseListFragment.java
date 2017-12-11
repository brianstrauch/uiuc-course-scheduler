package edu.illinois.finalproject.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.illinois.finalproject.model.Course;
import edu.illinois.finalproject.controller.CourseAdapter;
import edu.illinois.finalproject.R;

public class CourseListFragment extends Fragment implements CourseDialog.CourseDialogListener {
    private View view;

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
        view = inflater.inflate(R.layout.course_list, container, false);

        courses = new ArrayList<>();
        listener = (CourseListListener) getActivity();

        recyclerView = view.findViewById(R.id.rv_courses);
        setRecyclerView();

        addButton = view.findViewById(R.id.fab_add);
        setAddButton();

        return view;
    }

    /**
     * Creates the recycler view, allowing swipe to delete functionality.
     */
    private void setRecyclerView() {
        // Adapter
        courseAdapter = new CourseAdapter(courses);
        recyclerView.setAdapter(courseAdapter);

        // Layout Manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                courses.remove(position);
                listener.setCourses(courses);

                courseAdapter.notifyItemRemoved(position);
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    /**
     * Create the add button, which opens an "Add Course" dialog.
     */
    private void setAddButton() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    /**
     * Initialize and display an "Add Course" dialog
     */
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
            System.out.println("Downloading " + urls[0]);
            try {
                InputStream input = urls[0].openStream();
                Serializer serializer = new Persister();
                return serializer.read(Course.class, input);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Course course) {
            if (course == null) {
                Toast.makeText(view.getContext(), "Couldn't retrieve class info.", Toast.LENGTH_SHORT).show();
                return;
            }

            courses.add(course);
            listener.setCourses(courses);

            int end = courses.size() - 1;
            courseAdapter.notifyItemInserted(end);
        }
    }
}
