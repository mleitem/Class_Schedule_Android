package com.example.meganleitem_c196pa.termscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meganleitem_c196pa.R;
import com.example.meganleitem_c196pa.termscheduler.Database.Repository;
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

public class ViewTerm extends AppCompatActivity {
    EditText editTitle;
    EditText editStart;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    EditText editEnd;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarEnd = Calendar.getInstance();
    TextView viewId;

    String title;
    String start;
    String end;
    int id;

    Repository repo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_term);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repo = new Repository(getApplication());
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editTitle = findViewById(R.id.edittermtitle);
        editStart = findViewById(R.id.edittermstart);
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

                new DatePickerDialog(ViewTerm.this, startDate, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editEnd = findViewById(R.id.edittermend);
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

                new DatePickerDialog(ViewTerm.this, endDate, myCalendarEnd.get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
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

        viewId = findViewById(R.id.viewtermid);

        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        id = getIntent().getIntExtra("id",-1);

        editTitle.setText(title);
        editStart.setText(start);
        editEnd.setText(end);
        viewId.setText(Integer.toString(id));

        RecyclerView recyclerView = findViewById(R.id.associatedCoursesRecyclerView);
        List<Course> associatedCourses = new ArrayList<>();
        for(Course c: repo.getAllCourses()){
            if(c.getTermId() == id) {
                associatedCourses.add(c);
            }
        }
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(associatedCourses);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.delete:
                List<Term> terms = repo.getAllTerms();
                Term term = null;
                for(int i = 0; i < terms.size(); i ++){
                    int j = Integer.parseInt(viewId.getText().toString());
                    int k = terms.get(i).getTermId();
                    if(j == k){
                        term = terms.get(i);
                    }
                }
                deleteTerm(term);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_viewterm, menu);
        return true;
    }

    public void deleteTerm(Term term) {
        List<Course> courses = repo.getAllCourses();
        int termID = Integer.parseInt(viewId.getText().toString());
        int numCourses = 0;
        for(int i = 0; i < courses.size(); i++){
            int n = courses.get(i).getTermId();
            if(n == termID){
                numCourses++;
            }
        }
        if (numCourses > 0) {
            Toast.makeText(ViewTerm.this, term.getTermTitle() + " cannot be deleted. Please remove associated courses.", Toast.LENGTH_LONG).show();
        }
        else {
            repo.delete(term);
            Toast.makeText(ViewTerm.this, term.getTermTitle() + " was deleted.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ViewTerm.this, TermList.class);
            startActivity(intent);
        }

    }
    public void updateStart(){
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        editStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    public void updateEnd(){
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        editEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public void saveTerm(View view) {
        Term term;

        if(id == -1) {
            int newId = repo.getAllTerms().get(repo.getAllTerms().size() - 1).getTermId() + 1;
            term = new Term(newId, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
            repo.insert(term);
        }
        else {
            term = new Term(id, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
            repo.update(term);
        }

        Toast.makeText(ViewTerm.this, "Save Successful", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ViewTerm.this, TermList.class);
        startActivity(intent);

    }
}

