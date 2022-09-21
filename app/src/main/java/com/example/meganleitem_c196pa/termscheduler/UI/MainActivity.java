package com.example.meganleitem_c196pa.termscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meganleitem_c196pa.R;
import com.example.meganleitem_c196pa.termscheduler.Database.Repository;
import com.example.meganleitem_c196pa.termscheduler.Entity.Assessment;
import com.example.meganleitem_c196pa.termscheduler.Entity.Course;
import com.example.meganleitem_c196pa.termscheduler.Entity.Term;
import com.example.meganleitem_c196pa.termscheduler.Entity.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static int numAlert;

    EditText userUsername;
    EditText userPassword;

    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User user1 = new User(1, "test", "test");
        User user2 = new User(2, "admin", "admin");
        Repository repo = new Repository(getApplication());
        repo.insert(user1);
        repo.insert(user2);
    }

    public boolean loginCheck(String username, String password){
        Repository repo = new Repository(getApplication());
        List<User> allUsers = repo.getAllUsers();
        Boolean login = false;

        for(int i = 0; i < allUsers.size(); i++) {
            if (username.toLowerCase().equals(allUsers.get(i).getUsername().toLowerCase()) && password.equals(allUsers.get(i).getPassword())) {
                login = true;
            }
        }
        return login;
    }

    public void enterHere(View view) {
        userUsername = findViewById(R.id.username);
        userPassword = findViewById(R.id.password);
        username = userUsername.getText().toString();
        password = userPassword.getText().toString();

        if(loginCheck(username, password) == true){
            Intent intent = new Intent(MainActivity.this, TermList.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(MainActivity.this, "Incorrect username and/or password. Please try again.", Toast.LENGTH_LONG).show();
        }



    }
}