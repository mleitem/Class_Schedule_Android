package com.example.meganleitem_c196pa.termscheduler.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meganleitem_c196pa.R;
import com.example.meganleitem_c196pa.termscheduler.Database.Repository;
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

public class TermReports extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_reports);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.termReportsRecyclerView);
        List<Term> terms = repo.getAllTerms();
        final TermReportsAdapter adapter = new TermReportsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);
        editStart = findViewById(R.id.startDateTerm);
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

                new DatePickerDialog(TermReports.this, startDate, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editEnd = findViewById(R.id.endDateTerm);
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

                new DatePickerDialog(TermReports.this, endDate, myCalendarEnd.get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
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
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateStart(){
        editStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    public void updateEnd(){
        editEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public void submit(View view) throws ParseException {
        String startDate = editStart.getText().toString();
        String endDate = editEnd.getText().toString();
        List<Term> allTerms = repo.getAllTerms();
        List<Term> filteredTerms = new ArrayList<>();


        if(startDate.isEmpty() || endDate.isEmpty()){
            Toast.makeText(TermReports.this, "Please add a start and/or end date.", Toast.LENGTH_LONG).show();
            RecyclerView recyclerView = findViewById(R.id.termReportsRecyclerView);
            final TermReportsAdapter adapter = new TermReportsAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setTerms(allTerms);
        }
        else {
            LocalDate userStart = LocalDate.from(dtf.parse(startDate));
            LocalDate userEnd = LocalDate.from(dtf.parse(endDate));

            if (userStart.isAfter(userEnd)) {
                Toast.makeText(TermReports.this, "Please adjust your date range to the start date being after the end date.", Toast.LENGTH_LONG).show();
                RecyclerView recyclerView = findViewById(R.id.termReportsRecyclerView);
                final TermReportsAdapter adapter = new TermReportsAdapter(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setTerms(allTerms);
            }
            else {
                for (int i = 0; i < allTerms.size(); i++) {
                    Term term = allTerms.get(i);
                    String termStartString = term.getStartDate();
                    String termEndString = term.getEndDate();
                    LocalDate termStart = LocalDate.from(dtf.parse(termStartString));
                    LocalDate termEnd = LocalDate.from(dtf.parse(termEndString));
                    termStart = termStart.minusDays(1);
                    termEnd = termEnd.plusDays(1);

                    if (((userStart.isBefore(termStart)) && (userEnd.isAfter(termEnd)))
                            || ((userStart.isAfter(termStart) && userStart.isBefore(termEnd)) && (userEnd.isAfter(termStart) && userEnd.isBefore(termEnd)))
                            || ((userStart.isAfter(termStart) && userStart.isBefore(termEnd)) && (userEnd.isAfter(termEnd)))
                            || ((userStart.isBefore(termStart)) && (userEnd.isAfter(termStart) && userEnd.isBefore(termEnd)))) {
                        filteredTerms.add(term);
                    }
                }
                if (filteredTerms.size() > 0) {
                    RecyclerView recyclerView = findViewById(R.id.termReportsRecyclerView);
                    final TermReportsAdapter adapter = new TermReportsAdapter(this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    adapter.setTerms(filteredTerms);
                } else {
                    Toast.makeText(TermReports.this, "Sorry, there are no terms within your date range.", Toast.LENGTH_LONG).show();
                    RecyclerView recyclerView = findViewById(R.id.termReportsRecyclerView);
                    final TermReportsAdapter adapter = new TermReportsAdapter(this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    adapter.setTerms(allTerms);
                }
            }
        }
    }
}
