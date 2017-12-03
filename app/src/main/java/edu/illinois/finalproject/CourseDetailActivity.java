package edu.illinois.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CourseDetailActivity extends AppCompatActivity {
    private TextView label;
    private EditText comment;
    private Button post;
    private TextView commentList;

    private Course course;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);

        Intent detailIntent = getIntent();
        course = detailIntent.getParcelableExtra("course");

        label = findViewById(R.id.tv_label);
        label.setText(course.getLabel());

        comment = findViewById(R.id.et_comment);
        post = findViewById(R.id.btn_post);
        commentList = findViewById(R.id.tv_comment_list);
    }
}
