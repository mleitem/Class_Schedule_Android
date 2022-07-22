package com.example.meganleitem_c196pa.termscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meganleitem_c196pa.R;
import com.example.meganleitem_c196pa.termscheduler.Database.Repository;
import com.example.meganleitem_c196pa.termscheduler.Entity.Term;

import java.util.Objects;

public class ViewTerm extends AppCompatActivity {
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
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

        editTitle = findViewById(R.id.edittermtitle);
        editStart = findViewById(R.id.edittermstart);
        editEnd = findViewById(R.id.edittermend);
        viewId = findViewById(R.id.viewtermid);

        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        id = getIntent().getIntExtra("id",-1);


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

        Intent intent = new Intent(ViewTerm.this, TermList.class);
        startActivity(intent);

    }
}

