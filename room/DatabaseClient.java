package com.example.admin.sample.room;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseClient {
    private Context mCtx;
    private AppDatabase appDatabase;
    private static DatabaseClient mInstance;

    private DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;
        appDatabase = Room.databaseBuilder(mCtx.getApplicationContext(),
                 AppDatabase.class, "user_db")
                .build();
    }
    public static DatabaseClient getInstance(Context mCtx){
        if(mInstance == null){
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }
    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
