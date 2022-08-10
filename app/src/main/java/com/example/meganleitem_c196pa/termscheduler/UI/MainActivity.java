package com.example.meganleitem_c196pa.termscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.meganleitem_c196pa.R;
import com.example.meganleitem_c196pa.termscheduler.Database.Repository;
import com.example.meganleitem_c196pa.termscheduler.Entity.Assessment;
import com.example.meganleitem_c196pa.termscheduler.Entity.Course;
import com.example.meganleitem_c196pa.termscheduler.Entity.Term;

public class MainActivity extends AppCompatActivity {

    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enterHere(View view) {

        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);
        Repository repo = new Repository(getApplication());
        /*Term term = new Term(2, "Fall 2022", "10/1/2022", "12/1/2022");
        Course course = new Course(1, "Anatomy", "6/1/2022", "9/1/2022", "In Progress", "Name", "Email", "800-400-3000", 1);
        Assessment assessment = new Assessment(1, "Objective Assessment", "Anatomy Assessment", "08/10/2022", "08/11/2022", 1);
        repo.insert(term);
        repo.insert(course);
        repo.insert(assessment);*/
    }
}