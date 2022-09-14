package com.example.meganleitem_c196pa.termscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meganleitem_c196pa.R;
import com.example.meganleitem_c196pa.termscheduler.Database.Repository;
import com.example.meganleitem_c196pa.termscheduler.Entity.Assessment;
import com.example.meganleitem_c196pa.termscheduler.Entity.Term;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TermList extends AppCompatActivity {

    EditText searchTerms;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Term> terms = repo.getAllTerms();
        final TermAdapter adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);
        searchTerms = findViewById(R.id.searchTerms);
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

    public void searchTerms(View view) {
        String title = searchTerms.getText().toString();
        Repository repo = new Repository(getApplication());
        List<Term> filteredTerms = new ArrayList<>();
        List<Term> allTerms = repo.getAllTerms();

        for(int i = 0; i < allTerms.size(); i++) {
            Term term = allTerms.get(i);
            String termTitle = term.getTermTitle();
            if(termTitle.contains(title)){
                filteredTerms.add(term);
            }
        }
        if(searchTerms.getText().toString().isEmpty()){
            RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
            final TermAdapter adapter = new TermAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setTerms(allTerms);
        }
        if(filteredTerms.size() > 0) {
            RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
            final TermAdapter adapter = new TermAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setTerms(filteredTerms);
        }
        else {
            Toast.makeText(TermList.this, "Sorry, no term titles match your search.", Toast.LENGTH_LONG).show();
            RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
            final TermAdapter adapter = new TermAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setTerms(allTerms);
        }

    }

    public void enterCourseList(View view) {
        Intent intent = new Intent(TermList.this, CourseList.class);
        startActivity(intent);
    }

    public void enterAssessmentList(View view) {
        Intent intent = new Intent(TermList.this, AssessmentList.class);
        startActivity(intent);
    }

    public void enterNewTerm(View view) {
        Intent intent = new Intent(TermList.this, ViewTerm.class);
        startActivity(intent);
    }
}