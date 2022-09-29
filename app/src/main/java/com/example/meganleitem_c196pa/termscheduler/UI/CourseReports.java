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
import com.example.meganleitem_c196pa.termscheduler.Entity.Course;
import com.example.meganleitem_c196pa.termscheduler.Entity.Term;

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

public class CourseReports extends AppCompatActivity {

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
    Spinner courseStatus;
    Spinner courseInstructor;
    List<Course> allCourses = repo.getAllCourses();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_reports);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.courseReportsRecyclerView);
        List<Course> courses = repo.getAllCourses();
        final CourseReportsAdapter adapter = new CourseReportsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(courses);
        courseStatus = (Spinner) findViewById(R.id.statusSpinner);
        ArrayAdapter<CharSequence> courseReportsAdapter = ArrayAdapter.createFromResource(this, R.array.course_status_reports, android.R.layout.simple_spinner_item);
        courseReportsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        courseStatus.setAdapter(courseReportsAdapter);
        courseInstructor = (Spinner) findViewById(R.id.instructorSpinner);
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
        courseInstructor.setAdapter(courseArrayAdapter);
        editStart = findViewById(R.id.startDateCourse);
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

                new DatePickerDialog(CourseReports.this, startDate, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editEnd = findViewById(R.id.endDateCourse);
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

                new DatePickerDialog(CourseReports.this, endDate, myCalendarEnd.get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
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

    public List<Course> filterDates(String start, String end, List<Course> courses) {
        List<Course> filteredCourses = new ArrayList<>();
        LocalDate userStart = LocalDate.from(dtf.parse(start));
        LocalDate userEnd = LocalDate.from(dtf.parse(end));

            if (userStart.isAfter(userEnd)) {
                Toast.makeText(CourseReports.this, "Please adjust your date range to the start date being after the end date.", Toast.LENGTH_LONG).show();
                RecyclerView recyclerView = findViewById(R.id.courseReportsRecyclerView);
                final CourseReportsAdapter adapter = new CourseReportsAdapter(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setCourses(allCourses);
            } else {
                for (int i = 0; i < courses.size(); i++) {
                    Course course = courses.get(i);
                    String courseStartString = course.getStartDate();
                    String courseEndString = course.getEndDate();
                    LocalDate courseStart = LocalDate.from(dtf.parse(courseStartString));
                    LocalDate courseEnd = LocalDate.from(dtf.parse(courseEndString));

                    if (userEnd.isBefore(courseStart) || userStart.isAfter(courseEnd)) {

                    } else {
                        filteredCourses.add(course);
                    }
                }
            }

        return filteredCourses;
    }

    public List<Course> filterStatus(String status, List<Course> courses){
        List<Course> filteredCourses = new ArrayList<>();
        if(status.equals("All")){
            filteredCourses = courses;
        }
        else {
            for(int i = 0; i < courses.size(); i++){
                String courseStatus = courses.get(i).getStatus();
                if(courseStatus.equals(status)){
                    filteredCourses.add(courses.get(i));
                }
            }
        }
        return filteredCourses;
    }

    public List<Course> filterInstructor(String instructor, List<Course> courses){
        List<Course> filteredCourses = new ArrayList<>();
        if(instructor.equals("All")){
            filteredCourses = courses;
        }
        else {
            for(int i = 0; i < courses.size(); i++){
                String courseInstructor = courses.get(i).getInstructorName();
                if(courseInstructor.equals(instructor)){
                    filteredCourses.add(courses.get(i));
                }
            }
        }
        return filteredCourses;
    }

    public void submitCourseSearch(View view) throws ParseException {
        List<Course> searchedCourses = new ArrayList<>();
        List<Course> finalList = new ArrayList<>();
        String startDate = editStart.getText().toString();
        String endDate = editEnd.getText().toString();
        String status = courseStatus.getSelectedItem().toString();
        String instructor = courseInstructor.getSelectedItem().toString();

        if ((startDate.isEmpty() && !endDate.isEmpty()) || (!startDate.isEmpty() && endDate.isEmpty())) {
            Toast.makeText(CourseReports.this, "Please add a start or end date.", Toast.LENGTH_LONG).show();
            RecyclerView recyclerView = findViewById(R.id.courseReportsRecyclerView);
            final CourseReportsAdapter adapter = new CourseReportsAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setCourses(allCourses);
        }

        else if (startDate.isEmpty() && endDate.isEmpty()){
            finalList = filterStatus(status, allCourses);
            finalList = filterInstructor(instructor, finalList);
            if (finalList.size() > 0) {
                RecyclerView recyclerView = findViewById(R.id.courseReportsRecyclerView);
                final CourseReportsAdapter adapter = new CourseReportsAdapter(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setCourses(finalList);
            } else {
                Toast.makeText(CourseReports.this, "Sorry, there are no courses matching your search.", Toast.LENGTH_LONG).show();
                RecyclerView recyclerView = findViewById(R.id.courseReportsRecyclerView);
                final CourseReportsAdapter adapter = new CourseReportsAdapter(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setCourses(allCourses);
            }
        }

        else {
            finalList = filterDates(startDate, endDate, allCourses);
            finalList = filterStatus(status, finalList);
            finalList = filterInstructor(instructor, finalList);

            if (finalList.size() > 0) {
                RecyclerView recyclerView = findViewById(R.id.courseReportsRecyclerView);
                final CourseReportsAdapter adapter = new CourseReportsAdapter(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setCourses(finalList);
            } else {
                Toast.makeText(CourseReports.this, "Sorry, there are no courses matching your search.", Toast.LENGTH_LONG).show();
                RecyclerView recyclerView = findViewById(R.id.courseReportsRecyclerView);
                final CourseReportsAdapter adapter = new CourseReportsAdapter(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setCourses(allCourses);
            }
        }
    }
}


