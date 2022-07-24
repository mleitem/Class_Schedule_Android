package com.example.meganleitem_c196pa.termscheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meganleitem_c196pa.R;
import com.example.meganleitem_c196pa.termscheduler.Database.Repository;
import com.example.meganleitem_c196pa.termscheduler.Entity.Assessment;
import com.example.meganleitem_c196pa.termscheduler.Entity.Course;
import com.example.meganleitem_c196pa.termscheduler.Entity.Term;

import java.util.ArrayList;
import java.util.Objects;

public class ViewAssessment extends AppCompatActivity {
    Spinner assessmentType;
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    TextView viewId;
    Spinner associatedCourse;



    String type;
    String title;
    String start;
    String end;
    int id;
    int course;

    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assessment);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repo = new Repository(getApplication());

        assessmentType = (Spinner) findViewById(R.id.assessmentTypeSpinner);
        ArrayAdapter<CharSequence> assessmentAdapter = ArrayAdapter.createFromResource(this,R.array.assessment_type,android.R.layout.simple_spinner_item);
        assessmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        assessmentType.setAdapter(assessmentAdapter);
        editTitle = findViewById(R.id.editassessmenttitle);
        editStart = findViewById(R.id.editassessmentstart);
        editEnd = findViewById(R.id.editassessmentend);
        viewId = findViewById(R.id.viewassessmentid);
        associatedCourse = (Spinner) findViewById(R.id.associatedCourseSpinner);
        ArrayList<Course> courseList = new ArrayList<>();
        for(Course c: repo.getAllCourses()) {
            courseList.add(c);
        }
        ArrayAdapter<Course> courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseList);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        associatedCourse.setAdapter(courseAdapter);

        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        id = getIntent().getIntExtra("id", -1);
        course = getIntent().getIntExtra("course id", -1);

        editTitle.setText(title);
        editStart.setText(start);
        editEnd.setText(end);
        viewId.setText(Integer.toString(id));

        if(id != -1) {
            for (int i = 0; i < courseList.size(); ++i) {
                if (course == courseList.get(i).getCourseId()) {
                    int position = courseAdapter.getPosition(courseList.get(i));
                    associatedCourse.setSelection(position);
                }
            }
            for (int i = 0; i < assessmentAdapter.getCount(); ++i) {
                String mType = assessmentAdapter.getItem(i).toString();
                if (mType.matches(type)) {
                    int position = assessmentAdapter.getPosition(assessmentAdapter.getItem(i));
                    assessmentType.setSelection(position);
                }
            }
        }

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

    public void saveAssessment(View view) {
        Assessment assessment;
        Course course = (Course) associatedCourse.getSelectedItem();
        int courseId = course.getCourseId();

        String newType = assessmentType.getSelectedItem().toString();

        if(id == -1) {
            int newId = repo.getAllAssessments().get(repo.getAllAssessments().size() - 1).getAssessmentId() + 1;

            assessment = new Assessment(newId, newType, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), courseId);
            repo.insert(assessment);
        }
        else {
            assessment = new Assessment(id, newType, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), courseId);
            repo.update(assessment);
        }

        Intent intent = new Intent(ViewAssessment.this, AssessmentList.class);
        startActivity(intent);


    }
}

