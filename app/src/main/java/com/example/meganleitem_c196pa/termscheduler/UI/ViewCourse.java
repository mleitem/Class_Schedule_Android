package com.example.meganleitem_c196pa.termscheduler.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meganleitem_c196pa.R;
import com.example.meganleitem_c196pa.termscheduler.Database.Repository;
import com.example.meganleitem_c196pa.termscheduler.Entity.Assessment;
import com.example.meganleitem_c196pa.termscheduler.Entity.Course;
import com.example.meganleitem_c196pa.termscheduler.Entity.Term;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ViewCourse extends AppCompatActivity {
    EditText editTitle;
    EditText editStart;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    EditText editEnd;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarEnd = Calendar.getInstance();
    Spinner courseStatus;
    EditText editInstructorName;
    EditText editInstructorEmail;
    EditText editInstructorPhone;
    TextView viewCourseId;
    Spinner associatedTerm;
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

    String myFormat = "MM/dd/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

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
        editStart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Date date;
                Date today = Calendar.getInstance().getTime();
                String infoStart = editStart.getText().toString();
                if(infoStart.equals("")) {
                    myCalendarStart.setTime(today);
                }
                else {
                    try {
                        myCalendarStart.setTime(sdf.parse(infoStart));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                new DatePickerDialog(ViewCourse.this, startDate, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEnd = findViewById(R.id.editcourseend);
        editEnd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Date date;
                Date today = Calendar.getInstance().getTime();
                String infoEnd = editEnd.getText().toString();
                if(infoEnd.equals("")) {
                    myCalendarEnd.setTime(today);
                }
                else {
                    try {
                        myCalendarEnd.setTime(sdf.parse(infoEnd));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                new DatePickerDialog(ViewCourse.this, endDate, myCalendarEnd.get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateStart();
            }
        };

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateEnd();
            }
        };

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

        if(id != -1) {
            for (int i = 0; i < termList.size(); ++i) {
                if (termId == termList.get(i).getTermId()) {
                    int position = termAdapter.getPosition(termList.get(i));
                    associatedTerm.setSelection(position);
                }
            }

            for (int i = 0; i < courseAdapter.getCount(); ++i) {
                String mStatus = courseAdapter.getItem(i).toString();
                if (mStatus.matches(status)) {
                    int position = courseAdapter.getPosition(courseAdapter.getItem(i));
                    courseStatus.setSelection(position);
                }
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_viewcourse, menu);
        return true;
    }

    public void updateStart(){
        editStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    public void updateEnd(){
        editEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String t = editTitle.getText().toString();
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.share:
                Intent sendIntent = new Intent();
                String note = editNote.getText().toString();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, note);
                sendIntent.putExtra(Intent.EXTRA_TITLE, "My Course Notes");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.notifystart:
                String startDateFromScreen = editStart.getText().toString();
                Date startDate = null;
                try {
                    startDate = sdf.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long startTrigger = startDate.getTime();
                Intent startIntent = new Intent(ViewCourse.this, MyReceiver.class);
                startIntent.putExtra("type", "Course: " + t + " starts today." );
                PendingIntent startSender = PendingIntent.getBroadcast(ViewCourse.this, MainActivity.numAlert++, startIntent,0);
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAlarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, startSender);

                return true;
            case R.id.notifyend:
                String endDateFromScreen = editEnd.getText().toString();
                Date endDate = null;
                try {
                    endDate = sdf.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long endTrigger = endDate.getTime();
                Intent endIntent = new Intent(ViewCourse.this, MyReceiver.class);
                endIntent.putExtra("type", "Course: " + t + " ends today." );
                PendingIntent endSender = PendingIntent.getBroadcast(ViewCourse.this, MainActivity.numAlert++, endIntent,0);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, endSender);

                return true;
            case R.id.delete:
                List<Course> courses = repo.getAllCourses();
                Course course = null;
                for(int i = 0; i < courses.size(); i ++){
                    int j = Integer.parseInt(viewCourseId.getText().toString());
                    int k = courses.get(i).getCourseId();
                    if(j == k){
                        course = courses.get(i);
                    }
                }
                repo.delete(course);
                Toast.makeText(ViewCourse.this, course.getCourseTitle() + " was deleted.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ViewCourse.this, CourseList.class);
                startActivity(intent);
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

        Toast.makeText(ViewCourse.this, "Save Successful", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ViewCourse.this, CourseList.class);
        startActivity(intent);

    }
}

