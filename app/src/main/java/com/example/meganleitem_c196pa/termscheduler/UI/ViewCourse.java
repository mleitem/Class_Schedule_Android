package com.example.meganleitem_c196pa.termscheduler.UI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meganleitem_c196pa.R;

import java.util.Objects;

public class ViewCourse extends AppCompatActivity {
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    EditText editStatus;
    EditText editInstructorName;
    EditText editInstructorEmail;
    EditText editInstructorPhone;

    String title;
    String start;
    String end;
    String status;
    String instructorName;
    String instructorEmail;
    String instructorPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editTitle = findViewById(R.id.editcoursetitle);
        editStart = findViewById(R.id.editcoursestart);
        editEnd = findViewById(R.id.editcourseend);
        editStatus = findViewById(R.id.editcoursestatus);
        editInstructorName = findViewById(R.id.editinstructorname);
        editInstructorEmail = findViewById(R.id.editinstructoremail);
        editInstructorPhone = findViewById(R.id.editinstructorphone);

        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        status = getIntent().getStringExtra("status");
        instructorName = getIntent().getStringExtra("instructor name");
        instructorEmail = getIntent().getStringExtra("instructor email");
        instructorPhone = getIntent().getStringExtra("instructor phone");


        editTitle.setText(title);
        editStart.setText(start);
        editEnd.setText(end);
        editStatus.setText(status);
        editInstructorName.setText(instructorName);
        editInstructorEmail.setText(instructorEmail);
        editInstructorPhone.setText(instructorPhone);
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

    public void saveTerm(View view) {


    }
}

