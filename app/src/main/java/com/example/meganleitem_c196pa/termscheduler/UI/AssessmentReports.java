package com.example.meganleitem_c196pa.termscheduler.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meganleitem_c196pa.R;
import com.example.meganleitem_c196pa.termscheduler.Database.Repository;
import com.example.meganleitem_c196pa.termscheduler.Entity.Assessment;
import com.example.meganleitem_c196pa.termscheduler.Entity.Course;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AssessmentReports extends AppCompatActivity {

    String myFormat = "MM/dd/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(myFormat);
    EditText editStart;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    EditText editEnd;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarEnd = Calendar.getInstance();
    Repository repo = new Repository(getApplication());
    Spinner assessmentType;
    List<Assessment> allAssessments = repo.getAllAssessments();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_reports);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.assessmentReportsRecyclerView);
        List<Assessment> assessments = repo.getAllAssessments();
        final AssessmentReportsAdapter adapter = new AssessmentReportsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(assessments);
        assessmentType = (Spinner) findViewById(R.id.typeSpinner);
        ArrayAdapter<CharSequence> assessmentReportsAdapter = ArrayAdapter.createFromResource(this, R.array.assessment_type_reports, android.R.layout.simple_spinner_item);
        assessmentReportsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        assessmentType.setAdapter(assessmentReportsAdapter);
        /*courseInstructor = (Spinner) findViewById(R.id.instructorSpinner);
        ArrayList<String> instructorList = new ArrayList<>();
        instructorList.add("All");
        for (Course c : repo.getAllCourses()) {
            String name = c.getInstructorName();
            if (instructorList.contains(name)) {

            } else {
                instructorList.add(name);
            }
        }
        ArrayAdapter<String> courseArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, instructorList);
        courseArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        courseInstructor.setAdapter(courseArrayAdapter);*/
        editStart = findViewById(R.id.startDateAssessment);
        editStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                Date today = Calendar.getInstance().getTime();
                String infoStart = editStart.getText().toString();
                if (infoStart.equals("")) {
                    myCalendarStart.setTime(today);
                } else {
                    try {
                        myCalendarStart.setTime(sdf.parse(infoStart));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                new DatePickerDialog(AssessmentReports.this, startDate, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editEnd = findViewById(R.id.endDateAssessment);
        editEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date today = Calendar.getInstance().getTime();
                String infoEnd = editEnd.getText().toString();
                if (infoEnd.equals("")) {
                    myCalendarEnd.setTime(today);
                } else {
                    try {
                        myCalendarEnd.setTime(sdf.parse(infoEnd));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                new DatePickerDialog(AssessmentReports.this, endDate, myCalendarEnd.get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateStart() {
        editStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    public void updateEnd() {
        editEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public List<Assessment> filterDates(String start, String end, List<Assessment> assessments) {
        List<Assessment> filteredAssessments = new ArrayList<>();
        LocalDate userStart = LocalDate.from(dtf.parse(start));
        LocalDate userEnd = LocalDate.from(dtf.parse(end));

            if (userStart.isAfter(userEnd)) {
                Toast.makeText(AssessmentReports.this, "Please adjust your date range to the start date being after the end date.", Toast.LENGTH_LONG).show();
                RecyclerView recyclerView = findViewById(R.id.assessmentReportsRecyclerView);
                final AssessmentReportsAdapter adapter = new AssessmentReportsAdapter(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setAssessments(allAssessments);
            } else {
                for (int i = 0; i < assessments.size(); i++) {
                    Assessment assessment = assessments.get(i);
                    String assessmentStartString = assessment.getStartDate();
                    String assessmentEndString = assessment.getEndDate();
                    LocalDate assessmentStart = LocalDate.from(dtf.parse(assessmentStartString));
                    LocalDate assessmentEnd = LocalDate.from(dtf.parse(assessmentEndString));

                    if (userEnd.isBefore(assessmentStart) || userStart.isAfter(assessmentEnd)) {

                    } else {
                        filteredAssessments.add(assessment);
                    }
                }
            }

        return filteredAssessments;
    }

    public List<Assessment> filterType(String type, List<Assessment> assessments){
        List<Assessment> filteredAssessments = new ArrayList<>();
        if(type.equals("All")){
            filteredAssessments = assessments;
        }
        else {
            for(int i = 0; i < assessments.size(); i++){
                String assessmentType = assessments.get(i).getType();
                if(assessmentType.equals(type)){
                    filteredAssessments.add(assessments.get(i));
                }
            }
        }
        return filteredAssessments;
    }

    public void submitAssessmentSearch(View view) throws ParseException {
        List<Assessment> finalList = new ArrayList<>();
        String startDate = editStart.getText().toString();
        String endDate = editEnd.getText().toString();
        String type = assessmentType.getSelectedItem().toString();

        if ((startDate.isEmpty() && !endDate.isEmpty()) || (!startDate.isEmpty() && endDate.isEmpty())) {
            Toast.makeText(AssessmentReports.this, "Please add a start or end date.", Toast.LENGTH_LONG).show();
            RecyclerView recyclerView = findViewById(R.id.assessmentReportsRecyclerView);
            final AssessmentReportsAdapter adapter = new AssessmentReportsAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setAssessments(allAssessments);
        }

        else if (startDate.isEmpty() && endDate.isEmpty()){
            finalList = filterType(type, allAssessments);
            if (finalList.size() > 0) {
                RecyclerView recyclerView = findViewById(R.id.assessmentReportsRecyclerView);
                final AssessmentReportsAdapter adapter = new AssessmentReportsAdapter(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setAssessments(finalList);
            } else {
                Toast.makeText(AssessmentReports.this, "Sorry, there are no assessments matching your search.", Toast.LENGTH_LONG).show();
                RecyclerView recyclerView = findViewById(R.id.assessmentReportsRecyclerView);
                final AssessmentReportsAdapter adapter = new AssessmentReportsAdapter(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setAssessments(allAssessments);
            }
        }

        else {
            finalList = filterDates(startDate, endDate, allAssessments);
            finalList = filterType(type, finalList);

            if (finalList.size() > 0) {
                RecyclerView recyclerView = findViewById(R.id.assessmentReportsRecyclerView);
                final AssessmentReportsAdapter adapter = new AssessmentReportsAdapter(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setAssessments(finalList);
            } else {
                Toast.makeText(AssessmentReports.this, "Sorry, there are no assessments matching your search.", Toast.LENGTH_LONG).show();
                RecyclerView recyclerView = findViewById(R.id.assessmentReportsRecyclerView);
                final AssessmentReportsAdapter adapter = new AssessmentReportsAdapter(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setAssessments(allAssessments);
            }
        }
    }
}


