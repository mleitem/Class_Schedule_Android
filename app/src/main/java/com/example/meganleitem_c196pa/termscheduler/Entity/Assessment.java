package com.example.meganleitem_c196pa.termscheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.meganleitem_c196pa.termscheduler.Database.Repository;

import java.util.Date;

@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;

    private String type;
    private String assessmentTitle;
    private String startDate;
    private String endDate;

    private int courseId;

    public Assessment(int assessmentId, String type, String assessmentTitle, String startDate, String endDate, int courseId) {
        this.assessmentId = assessmentId;
        this.type = type;
        this.assessmentTitle = assessmentTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseId = courseId;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

}
