package com.example.admin.sample.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);
    
    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user_table")
    List<User> getAll();

    @Query("SELECT * FROM user_table WHERE email_id =:emailid ")
    List<User> getUser(String emailid);
}