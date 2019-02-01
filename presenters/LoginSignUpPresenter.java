package com.example.admin.sample.presenters;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.example.admin.sample.MainActivity;
import com.example.admin.sample.contracts.ILoginSignUpContracts;
import com.example.admin.sample.room.AppDatabase;
import com.example.admin.sample.room.DatabaseClient;
import com.example.admin.sample.room.User;

import java.util.List;

public class LoginSignUpPresenter implements ILoginSignUpContracts.presenter {
    ILoginSignUpContracts.view view;

    public LoginSignUpPresenter(ILoginSignUpContracts.view view) {
        this.view = view;
    }


    @Override
    public void getUserList(final AppDatabase appDatabase, final String email) {
        class GetTasks extends AsyncTask<Void, Void, List<User>> {

            @Override
            protected List<User> doInBackground(Void... voids) {
                List<User> UserList = appDatabase
                        .userDao()
                        .getUser(email);
                return UserList;
            }

            @Override
            protected void onPostExecute(List<User> UsersTables) {
                super.onPostExecute(UsersTables);
                Log.d("response==>", "onPostExecute: " + UsersTables.size());
                if (UsersTables.size() == 0) {
                    view.failureResponse("You have Not Registered!");
                } else {
                    view.successResponse(UsersTables.get(0));
                }

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();

    }


}
