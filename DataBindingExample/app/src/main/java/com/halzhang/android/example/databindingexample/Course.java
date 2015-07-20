package com.halzhang.android.example.databindingexample;

/**
 * Created by zhanghanguo@yy.com on 2015/6/4.
 */
public class Course {

    public final String name;
    public final String teacher;
    public final long startTime;

    public Course(String name, String teacher, long startTime) {
        this.name = name;
        this.teacher = teacher;
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public long getStartTime() {
        return startTime;
    }
}
