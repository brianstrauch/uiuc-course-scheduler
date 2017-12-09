package edu.illinois.finalproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.illinois.finalproject.R;
import edu.illinois.finalproject.model.Course;

public class CourseDetailActivity extends AppCompatActivity {
    private Course course;

    FirebaseDatabase database;
    DatabaseReference messageListRef;

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

        database = FirebaseDatabase.getInstance();
        messageListRef = database.getReference(course.getId());

        setLabel();
        setId();
        setDescription();
        setComments();
    }

    private void setLabel() {
        label = findViewById(R.id.tv_label);
        label.setText(course.getLabel());
    }

    private void setId() {
        id = findViewById(R.id.tv_id);
        id.setText(course.getId());
    }

    private void setDescription() {
        description = findViewById(R.id.tv_description);
        description.setText(course.getDescription());
    }

    private void setComments() {
        comments = findViewById(R.id.tv_comment_list);
        messageListRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String message = dataSnapshot.getValue(String.class);
                comments.append(message + "\n");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        comment = findViewById(R.id.et_comment);
        post = findViewById(R.id.btn_post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comment.getText().toString().length() > 0) {
                    DatabaseReference messageRef = messageListRef.push();
                    messageRef.setValue(comment.getText().toString());
                    comment.setText("");
                }
            }
        });
    }


}
