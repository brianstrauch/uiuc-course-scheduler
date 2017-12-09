package edu.illinois.finalproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.illinois.finalproject.R;
import edu.illinois.finalproject.model.Course;

public class CourseDetailActivity extends AppCompatActivity {
    private Course course;

    private TextView label;
    private TextView id;
    private TextView description;

    private TextView comments;
    private EditText comment;
    private Button post;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);

        Intent detailIntent = getIntent();
        course = detailIntent.getParcelableExtra("course");

        label = findViewById(R.id.tv_label);
        label.setText(course.getLabel());

        id = findViewById(R.id.tv_id);
        id.setText(course.getId());

        description = findViewById(R.id.tv_description);
        description.setText(course.getDescription());

        comments = findViewById(R.id.tv_comments);
        comment = findViewById(R.id.et_comment);
        post = findViewById(R.id.btn_post);
    }


}
