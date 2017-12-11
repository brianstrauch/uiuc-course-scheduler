package edu.illinois.finalproject.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import edu.illinois.finalproject.R;
import edu.illinois.finalproject.model.Course;

public class CourseDialog extends DialogFragment {
    private EditText department;
    private EditText number;

    private CourseDialogListener listener;
    public interface CourseDialogListener {
        void addCourse(Course course);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.course_dialog, null);

        department = view.findViewById(R.id.et_department);
        number = view.findViewById(R.id.et_number);

        listener = (CourseDialogListener) getTargetFragment();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Add Course")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Course course = new Course(department.getText().toString(), number.getText().toString());
                        listener.addCourse(course);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {}
                });

        return builder.create();
    }
}
