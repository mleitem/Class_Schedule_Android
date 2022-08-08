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

public class ViewAssessment extends AppCompatActivity {
    Spinner assessmentType;
    EditText editTitle;
    EditText editStart;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    EditText editEnd;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarEnd = Calendar.getInstance();
    TextView viewId;
    Spinner associatedCourse;

    String type;
    String title;
    String start;
    String end;
    int id;
    int course;

    Repository repo;

    String myFormat = "MM/dd/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

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

                new DatePickerDialog(ViewAssessment.this, startDate, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editEnd = findViewById(R.id.editassessmentend);
        editEnd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
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

                new DatePickerDialog(ViewAssessment.this, endDate, myCalendarEnd.get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
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
        String t = editTitle.getText().toString();
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
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
                Intent startIntent = new Intent(ViewAssessment.this, MyReceiver.class);
                startIntent.putExtra("type", "Assessment: " + t + " starts today." );
                PendingIntent startSender = PendingIntent.getBroadcast(ViewAssessment.this, MainActivity.numAlert++, startIntent,0);
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
                Intent endIntent = new Intent(ViewAssessment.this, MyReceiver.class);
                endIntent.putExtra("type", "Assessment: " + t + " ends today." );
                PendingIntent endSender = PendingIntent.getBroadcast(ViewAssessment.this, MainActivity.numAlert++, endIntent,0);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, endSender);

                return true;
            case R.id.delete:
                List<Assessment> assessments = repo.getAllAssessments();
                Assessment assessment = null;
                for(int i = 0; i < assessments.size(); i ++){
                    int j = Integer.parseInt(viewId.getText().toString());
                    int k = assessments.get(i).getAssessmentId();
                    if(j == k){
                        assessment = assessments.get(i);
                    }
                }
                repo.delete(assessment);
                Toast.makeText(ViewAssessment.this, assessment.getAssessmentTitle() + " was deleted.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ViewAssessment.this, AssessmentList.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_viewassessment, menu);
        return true;
    }

    public void updateStart(){
        editStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    public void updateEnd(){
        editEnd.setText(sdf.format(myCalendarEnd.getTime()));
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

        Toast.makeText(ViewAssessment.this, "Save Successful", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ViewAssessment.this, AssessmentList.class);
        startActivity(intent);


    }
}

