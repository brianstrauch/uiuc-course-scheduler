package edu.illinois.finalproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    private List<Course> courses;

    public CourseAdapter(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.course_item, parent, false);
        CourseHolder courseHolder = new CourseHolder(view);
        return courseHolder;
    }

    @Override
    public void onBindViewHolder(CourseHolder holder, int position) {
        final Course course = courses.get(position);
        holder.populate(course);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                Intent courseDetailIntent = new Intent(context, CourseDetailActivity.class);
                courseDetailIntent.putExtra("course", course);
                context.startActivity(courseDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class CourseHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public CourseHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_course_name);
        }

        public void populate(Course course) {
            name.setText(course.getId());
        }
    }
}