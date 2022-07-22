package com.example.meganleitem_c196pa.termscheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meganleitem_c196pa.R;
import com.example.meganleitem_c196pa.termscheduler.Database.Repository;
import com.example.meganleitem_c196pa.termscheduler.Entity.Assessment;
import com.example.meganleitem_c196pa.termscheduler.Entity.Term;

import java.util.Objects;

public class ViewAssessment extends AppCompatActivity {
    EditText editType;
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    TextView viewId;

    String type;
    String title;
    String start;
    String end;
    int id;

    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assessment);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repo = new Repository(getApplication());

        editType = findViewById(R.id.editassessmenttype);
        editTitle = findViewById(R.id.editassessmenttitle);
        editStart = findViewById(R.id.editassessmentstart);
        editEnd = findViewById(R.id.editassessmentend);
        viewId = findViewById(R.id.viewassessmentid);

        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        id = getIntent().getIntExtra("id", -1);

        editType.setText(type);
        editTitle.setText(title);
        editStart.setText(start);
        editEnd.setText(end);
        viewId.setText(Integer.toString(id));
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

    public int findCourseId(int assessmentId) {
        int courseId = 0;
        for(Assessment a: repo.getAllAssessments()){
            if(assessmentId == a.getAssessmentId()){
                courseId = a.getCourseId();
            }
        }
        return courseId;
    }

    public void saveAssessment(View view) {
        Assessment assessment;
        int courseId = findCourseId(id);
        if(id == -1) {
            int newId = repo.getAllAssessments().get(repo.getAllAssessments().size() - 1).getAssessmentId() + 1;

            assessment = new Assessment(newId, editType.getText().toString(), editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), courseId);
            repo.insert(assessment);
        }
        else {
            assessment = new Assessment(id, editType.getText().toString(), editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), courseId);
            repo.update(assessment);
        }

        Intent intent = new Intent(ViewAssessment.this, AssessmentList.class);
        startActivity(intent);


    }
}

