package com.example.meganleitem_c196pa.termscheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.meganleitem_c196pa.termscheduler.Entity.Assessment;
import com.example.meganleitem_c196pa.termscheduler.Entity.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users ORDER BY userId ASC")
    List<User> getAllUsers();

}
