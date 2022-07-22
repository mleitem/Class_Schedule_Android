package com.example.meganleitem_c196pa.termscheduler.UI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meganleitem_c196pa.R;

import java.util.Objects;

public class ViewAssessment extends AppCompatActivity {
    EditText editType;
    EditText editTitle;
    EditText editStart;
    EditText editEnd;

    String type;
    String title;
    String start;
    String end;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assessment);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editType = findViewById(R.id.editassessmenttype);
        editTitle = findViewById(R.id.editassessmenttitle);
        editStart = findViewById(R.id.editassessmentstart);
        editEnd = findViewById(R.id.editassessmentend);

        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");

        editType.setText(type);
        editTitle.setText(title);
        editStart.setText(start);
        editEnd.setText(end);
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


    }
}

