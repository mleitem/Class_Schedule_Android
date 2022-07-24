package com.example.meganleitem_c196pa.termscheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meganleitem_c196pa.R;
import com.example.meganleitem_c196pa.termscheduler.Database.Repository;
import com.example.meganleitem_c196pa.termscheduler.Entity.Assessment;
import com.example.meganleitem_c196pa.termscheduler.Entity.Course;

import java.util.Objects;

public class ViewCourse extends AppCompatActivity {
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    EditText editStatus;
    EditText editInstructorName;
    EditText editInstructorEmail;
    EditText editInstructorPhone;
    TextView viewCourseId;
    EditText editTermId;


    String title;
    String start;
    String end;
    String status;
    String instructorName;
    String instructorEmail;
    String instructorPhone;
    int id;
    int termId;

    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repo = new Repository(getApplication());

        editTitle = findViewById(R.id.editcoursetitle);
        editStart = findViewById(R.id.editcoursestart);
        editEnd = findViewById(R.id.editcourseend);
        editStatus = findViewById(R.id.editcoursestatus);
        editInstructorName = findViewById(R.id.editinstructorname);
        editInstructorEmail = findViewById(R.id.editinstructoremail);
        editInstructorPhone = findViewById(R.id.editinstructorphone);
        viewCourseId = findViewById(R.id.viewcourseid);
        editTermId = findViewById(R.id.edittermid);

        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        status = getIntent().getStringExtra("status");
        instructorName = getIntent().getStringExtra("instructor name");
        instructorEmail = getIntent().getStringExtra("instructor email");
        instructorPhone = getIntent().getStringExtra("instructor phone");
        id = getIntent().getIntExtra("id", -1);
        termId = getIntent().getIntExtra("termid", -1);

        editTitle.setText(title);
        editStart.setText(start);
        editEnd.setText(end);
        editStatus.setText(status);
        editInstructorName.setText(instructorName);
        editInstructorEmail.setText(instructorEmail);
        editInstructorPhone.setText(instructorPhone);
        viewCourseId.setText(Integer.toString(id));
        editTermId.setText(Integer.toString(termId));
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

    public int findTermId(int courseId) {
        int termId = 0;
        for(Course c: repo.getAllCourses()){
            if(courseId == c.getCourseId()){
                termId = c.getTermId();
            }
        }
        return termId;
    }

    public void saveCourse(View view) {
        Course course;
        int termId = findTermId(id);
        int newTermId = Integer.parseInt((editTermId.getText().toString()));
        if(id == -1) {
            int newId = repo.getAllCourses().get(repo.getAllCourses().size() - 1).getCourseId() + 1;


            course = new Course(newId, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), editStatus.getText().toString(), editInstructorName.getText().toString(),
                    editInstructorEmail.getText().toString(), editInstructorPhone.getText().toString(), newTermId);
            repo.insert(course);
        }
        else {
            course = new Course(id, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), editStatus.getText().toString(), editInstructorName.getText().toString(),
                    editInstructorEmail.getText().toString(), editInstructorPhone.getText().toString(), newTermId);
            repo.update(course);
        }

        Intent intent = new Intent(ViewCourse.this, CourseList.class);
        startActivity(intent);

    }
}

