package edu.illinois.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

public class Course implements Parcelable {
    private String id;
    private String label;

    private int color;

    protected Course(Parcel in) {
        id = in.readString();
        label = in.readString();
        color = in.readInt();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(label);
        parcel.writeInt(color);
    }
}
