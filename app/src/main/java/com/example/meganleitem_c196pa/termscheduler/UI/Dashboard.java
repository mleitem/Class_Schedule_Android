package com.example.meganleitem_c196pa.termscheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meganleitem_c196pa.R;
import com.example.meganleitem_c196pa.termscheduler.Database.Repository;
import com.example.meganleitem_c196pa.termscheduler.Entity.User;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void enterTermList(View view){
        Intent intent = new Intent(Dashboard.this, TermList.class);
        startActivity(intent);
    }

    public void enterAssessmentList(View view) {
        Intent intent = new Intent(Dashboard.this, AssessmentList.class);
        startActivity(intent);
    }

    public void enterCourseList(View view){
        Intent intent = new Intent(Dashboard.this, CourseList.class);
        startActivity(intent);
    }
}
