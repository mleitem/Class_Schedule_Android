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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meganleitem_c196pa.R;
import com.example.meganleitem_c196pa.termscheduler.Database.Repository;
import com.example.meganleitem_c196pa.termscheduler.Entity.Assessment;
import com.example.meganleitem_c196pa.termscheduler.Entity.Course;
import com.example.meganleitem_c196pa.termscheduler.Entity.Term;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewCourse extends AppCompatActivity {
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    Spinner courseStatus;
    EditText editInstructorName;
    EditText editInstructorEmail;
    EditText editInstructorPhone;
    TextView viewCourseId;
    Spinner associatedTerm;
    EditText editTermId;
    EditText editNote;


    String title;
    String start;
    String end;
    String status;
    String instructorName;
    String instructorEmail;
    String instructorPhone;
    int id;
    int termId;
    String note;

    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repo = new Repository(getApplication());

        courseStatus = (Spinner) findViewById(R.id.courseStatusSpinner);
        ArrayAdapter<CharSequence> courseAdapter = ArrayAdapter.createFromResource(this,R.array.course_status,android.R.layout.simple_spinner_item);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        courseStatus.setAdapter(courseAdapter);
        editTitle = findViewById(R.id.editcoursetitle);
        editStart = findViewById(R.id.editcoursestart);
        editEnd = findViewById(R.id.editcourseend);
        editInstructorName = findViewById(R.id.editinstructorname);
        editInstructorEmail = findViewById(R.id.editinstructoremail);
        editInstructorPhone = findViewById(R.id.editinstructorphone);
        viewCourseId = findViewById(R.id.viewcourseid);
        associatedTerm = (Spinner) findViewById(R.id.associatedTermSpinner);
        ArrayList<Term> termList = new ArrayList<>();
        for(Term t: repo.getAllTerms()) {
            termList.add(t);
        }
        ArrayAdapter<Term> termAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, termList);
        termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        associatedTerm.setAdapter(termAdapter);
        editNote = findViewById(R.id.editnote);

        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        status = getIntent().getStringExtra("status");
        instructorName = getIntent().getStringExtra("instructor name");
        instructorEmail = getIntent().getStringExtra("instructor email");
        instructorPhone = getIntent().getStringExtra("instructor phone number");
        id = getIntent().getIntExtra("id", -1);
        termId = getIntent().getIntExtra("term id", -1);
        note = getIntent().getStringExtra("note");


        editTitle.setText(title);
        editStart.setText(start);
        editEnd.setText(end);
        editInstructorName.setText(instructorName);
        editInstructorEmail.setText(instructorEmail);
        editInstructorPhone.setText(instructorPhone);
        viewCourseId.setText(Integer.toString(id));
        editNote.setText(note);

        for(int i = 0; i < termList.size(); ++i){
            if(termId == termList.get(i).getTermId()){
                int position = termAdapter.getPosition(termList.get(i));
                associatedTerm.setSelection(position);
            }
        }

        for(int i = 0; i < courseAdapter.getCount(); ++i) {
            String mStatus = courseAdapter.getItem(i).toString();
            if(mStatus.matches(status)){
                int position = courseAdapter.getPosition(courseAdapter.getItem(i));
                courseStatus.setSelection(position);
            }
        }

        RecyclerView recyclerView = findViewById(R.id.associatedAssessmentsRecyclerView);
        List<Assessment> associatedAssessments = new ArrayList<>();
        for(Assessment a: repo.getAllAssessments()){
            if(a.getCourseId() == id) {
                associatedAssessments.add(a);
            }
        }
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(associatedAssessments);
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

    public void saveCourse(View view) {
        Course course;
        Term term = (Term) associatedTerm.getSelectedItem();
        int termId = term.getTermId();

        String newStatus = courseStatus.getSelectedItem().toString();

        if(id == -1) {
            int newId = repo.getAllCourses().get(repo.getAllCourses().size() - 1).getCourseId() + 1;


            course = new Course(newId, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), newStatus, editInstructorName.getText().toString(),
                    editInstructorEmail.getText().toString(), editInstructorPhone.getText().toString(), termId);
            course.setNote(editNote.getText().toString());

            repo.insert(course);
        }
        else {
            course = new Course(id, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), newStatus, editInstructorName.getText().toString(),
                    editInstructorEmail.getText().toString(), editInstructorPhone.getText().toString(), termId);
            course.setNote(editNote.getText().toString());
            repo.update(course);
        }

        Intent intent = new Intent(ViewCourse.this, CourseList.class);
        startActivity(intent);

    }
}

