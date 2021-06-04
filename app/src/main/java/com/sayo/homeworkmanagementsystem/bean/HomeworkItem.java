package com.sayo.homeworkmanagementsystem.bean;

public class HomeworkItem {

    private int homeworkId;
    private int teacherId;
    private String teacherName;
    private String homeworkTitle;
    private String homeworkContent;

    public HomeworkItem() {
    }

    public HomeworkItem(int homeworkId, int teacherId, String teacherName, String homeworkTitle, String homeworkContent) {
        this.homeworkId = homeworkId;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.homeworkTitle = homeworkTitle;
        this.homeworkContent = homeworkContent;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getHomeworkTitle() {
        return homeworkTitle;
    }

    public void setHomeworkTitle(String homeworkTitle) {
        this.homeworkTitle = homeworkTitle;
    }

    public String getHomeworkContent() {
        return homeworkContent;
    }

    public void setHomeworkContent(String homeworkContent) {
        this.homeworkContent = homeworkContent;
    }
}