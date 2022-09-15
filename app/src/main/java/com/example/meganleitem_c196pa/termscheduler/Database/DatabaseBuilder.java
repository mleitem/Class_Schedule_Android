package com.example.meganleitem_c196pa.termscheduler.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.meganleitem_c196pa.termscheduler.DAO.AssessmentDAO;
import com.example.meganleitem_c196pa.termscheduler.DAO.CourseDAO;
import com.example.meganleitem_c196pa.termscheduler.DAO.TermDAO;
import com.example.meganleitem_c196pa.termscheduler.DAO.UserDAO;
import com.example.meganleitem_c196pa.termscheduler.Entity.Assessment;
import com.example.meganleitem_c196pa.termscheduler.Entity.Course;
import com.example.meganleitem_c196pa.termscheduler.Entity.Term;
import com.example.meganleitem_c196pa.termscheduler.Entity.User;

@Database(entities={Term.class, Course.class, Assessment.class, User.class}, version=2, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract UserDAO userDAO();

    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context) {
        if(INSTANCE==null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "termDatabase").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

}
