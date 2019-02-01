package com.example.admin.sample.contracts;

import com.example.admin.sample.room.AppDatabase;
import com.example.admin.sample.room.User;

import java.util.List;

public interface ILoginSignUpContracts {
    interface view{
        public void failureResponse(String msg);
        public void successResponse(User user);
    }
    interface presenter{
        public void getUserList(AppDatabase appDatabase,String email);
    }
}
