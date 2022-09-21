package com.example.meganleitem_c196pa.termscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meganleitem_c196pa.R;
import com.example.meganleitem_c196pa.termscheduler.Database.Repository;
import com.example.meganleitem_c196pa.termscheduler.Entity.Assessment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AssessmentList extends AppCompatActivity {

    EditText searchAssessments;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Assessment> assessments = repo.getAllAssessments();
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(assessments);
        searchAssessments = findViewById(R.id.searchAssessments);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void searchAssessments(View view) {
        String title = searchAssessments.getText().toString().toLowerCase();
        Repository repo = new Repository(getApplication());
        List<Assessment> filteredAssessments = new ArrayList<>();
        List<Assessment> allAssessments = repo.getAllAssessments();

        for(int i = 0; i < allAssessments.size(); i++) {
            Assessment assessment = allAssessments.get(i);
            String assessmentTitle = assessment.getAssessmentTitle().toLowerCase();
            if(assessmentTitle.contains(title)){
                filteredAssessments.add(assessment);
            }
        }
        if(searchAssessments.getText().toString().isEmpty()){
            RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
            final AssessmentAdapter adapter = new AssessmentAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setAssessments(allAssessments);
        }
        if(filteredAssessments.size() > 0) {
            RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
            final AssessmentAdapter adapter = new AssessmentAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setAssessments(filteredAssessments);
        }
        else {
            Toast.makeText(AssessmentList.this, "Sorry, no assessment titles match your search.", Toast.LENGTH_LONG).show();
            RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
            final AssessmentAdapter adapter = new AssessmentAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setAssessments(allAssessments);
        }

    }

    public void enterTermList(View view){
        Intent intent = new Intent(AssessmentList.this, TermList.class);
        startActivity(intent);
    }

    public void enterCourseList(View view){
        Intent intent = new Intent(AssessmentList.this, CourseList.class);
        startActivity(intent);
    }

    public void enterNewAssessment(View view) {
        Intent intent = new Intent(AssessmentList.this, ViewAssessment.class);
        startActivity(intent);
    }
}