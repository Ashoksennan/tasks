package com.example.admin.sample;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.sample.contracts.ILoginSignUpContracts;
import com.example.admin.sample.fragments.TemplatePreviewFragment;
import com.example.admin.sample.presenters.LoginSignUpPresenter;
import com.example.admin.sample.room.DatabaseClient;
import com.example.admin.sample.room.User;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements ILoginSignUpContracts.view {
EditText et_mailid,et_password;
Button login_btn,sigup_btn;
LoginSignUpPresenter loginSignUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginSignUpPresenter = new LoginSignUpPresenter(this);
        et_mailid = findViewById(R.id.et_mailid);
        et_password = findViewById(R.id.et_password);
        login_btn = findViewById(R.id.login_btn);
        sigup_btn = findViewById(R.id.sigup_btn);

        onclicks();


    }

    private void onclicks() {
        sigup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmailValidate(et_mailid.getText().toString())){
                    if(isValidPassword(et_password.getText().toString())){
                        loginSignUpPresenter.getUserList( DatabaseClient.getInstance(getApplicationContext()).getAppDatabase(),et_mailid.getText().toString());
                    }else{
                        et_password.setError("Must have One Uppercase,One lowercase and one Numerical Value!");
                    }
                }else{
                    et_mailid.setError("Please Enter valid Email!");
                }
            }
        });
    }
    private boolean isEmailValidate(String email){
            return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }
    private void showDialog() {
        @SuppressLint("CommitTransaction")
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        TemplatePreviewFragment templatePreview = new TemplatePreviewFragment();
        templatePreview.show(getSupportFragmentManager(),"dialog");

    }

    @Override
    public void failureResponse(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successResponse(User user) {
        Toast.makeText(this, user.getUserName(), Toast.LENGTH_SHORT).show();
        if(user.getPassword().equals(et_password.getText().toString())){
            Intent movetoHome = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(movetoHome);
        }else{
            et_password.setError("Password wrong!");
        }
    }
}
