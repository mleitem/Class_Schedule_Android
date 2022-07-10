package com.example.meganleitem_c196pa.termscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.meganleitem_c196pa.R;

import java.util.Objects;

public class ViewTerm extends AppCompatActivity {
    EditText editTitle;
    EditText editStart;
    EditText editEnd;

    String title;
    String start;
    String end;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_term);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editTitle = findViewById(R.id.edittermtitle);
        editStart = findViewById(R.id.edittermstart);
        editEnd = findViewById(R.id.edittermend);

        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");

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

    public void saveTerm(View view) {


    }
}

