package com.example.meganleitem_c196pa.termscheduler.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meganleitem_c196pa.R;
import com.example.meganleitem_c196pa.termscheduler.Database.Repository;
import com.example.meganleitem_c196pa.termscheduler.Entity.Assessment;
import com.example.meganleitem_c196pa.termscheduler.Entity.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseList extends AppCompatActivity {

    EditText searchCourses;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Course> courses = repo.getAllCourses();
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(courses);
        searchCourses = findViewById(R.id.searchCourses);
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

    public void searchCourses(View view){
        String title = searchCourses.getText().toString().toLowerCase();
        Repository repo = new Repository(getApplication());
        List<Course> filteredCourses = new ArrayList<>();
        List<Course> allCourses = repo.getAllCourses();

        for(int i = 0; i < allCourses.size(); i++) {
            Course course = allCourses.get(i);
            String courseTitle = course.getCourseTitle().toLowerCase();
            if(courseTitle.contains(title)){
                filteredCourses.add(course);
            }
        }
        if(searchCourses.getText().toString().isEmpty()){
            RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
            final CourseAdapter adapter = new CourseAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setCourses(allCourses);
        }
        if(filteredCourses.size() > 0) {
            RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
            final CourseAdapter adapter = new CourseAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setCourses(filteredCourses);
        }
        else {
            Toast.makeText(CourseList.this, "Sorry, no course titles match your search.", Toast.LENGTH_LONG).show();
            RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
            final CourseAdapter adapter = new CourseAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setCourses(allCourses);
        }

    }

    public void enterTermList(View view){
        Intent intent = new Intent(CourseList.this, TermList.class);
        startActivity(intent);
    }

    public void enterAssessmentList(View view) {
        Intent intent = new Intent(CourseList.this, AssessmentList.class);
        startActivity(intent);
    }

    public void enterNewCourse(View view) {
        Intent intent = new Intent(CourseList.this, ViewCourse.class);
        startActivity(intent);
    }
}